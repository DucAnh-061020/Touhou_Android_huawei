package com.touhou.game.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.touhou.game.Camera.HUDScreen;
import com.touhou.game.Camera.LoadXmlDoc;
import com.touhou.game.Camera.PauseScreen;
import com.touhou.game.Character.Reimu;
import com.touhou.game.Core;
import com.touhou.game.Items.BPoint;
import com.touhou.game.Items.SPowerUp;
import com.touhou.game.Mobs.F0A1;
import com.touhou.game.Mobs.Fairies;
import com.touhou.game.THBoss.Boss;
import com.touhou.game.THBoss.FakeBoss;
import com.touhou.game.THUltilities.Bullet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

import static com.touhou.game.Core.GAME_STATUS;
import static com.touhou.game.Core.gameHeight;
import static com.touhou.game.Core.gameWidth;

public class Stage1 implements Screen, InputProcessor {
    private Core parent;

    private Viewport viewport;

    //graphics
    private SpriteBatch batch;
    private TextureAtlas bulletAtlas,fairiesAtlas;
    private TextureRegion[] backgroundTextureRegion;
    private float elapseTime;
    private HUDScreen hud;
    private PauseScreen pauseScreen;
    private Camera camera;

    //timing
    public static Random generator;
    private float[] backgroundOffset = {0,gameHeight,0,0};
    private float[] backgroundX = {0,0,0,0};
    private float backgroundMaxScrollSpeed;
    // wave control
    public float[] timeBettweenSpawnF0A1,timeBetweenWaveF0A1,numPerSpawn,totalNumPerWave;
    public float spawnTimeF0A1 = 0;
    private int waveF0A1Counter = 0;

    //Game objects
    //player
    Reimu player;

    //f0a1 mobs
    private F0A1[] f0A1Array;
    private int currentF0A1spawn = 0;

    //boss
    FakeBoss boss;
    //item
    private TextureAtlas itemAtlas;
    int score = 0;
    private int totalF0A1Wave;

    public Stage1(Core p) {
        parent = p;
        //game data
        generator = new Random((int) gameWidth);

        //camera
        camera = new OrthographicCamera();
        viewport = new FitViewport(gameWidth,gameHeight, camera);

        //background and scrolling background
        backgroundMaxScrollSpeed = gameHeight /2f;
        TextureAtlas backgroundAtlas = new TextureAtlas("Atlas/background.atlas");
        backgroundTextureRegion = new TextureRegion[4];
        backgroundTextureRegion[0] = backgroundAtlas.findRegion("background0");
        backgroundTextureRegion[1] = backgroundAtlas.findRegion("background0");
        backgroundTextureRegion[2] = backgroundAtlas.findRegion("background1");
        backgroundTextureRegion[3] = backgroundAtlas.findRegion("background2");
        for(int layer = 1; layer < backgroundTextureRegion.length; layer++){
            backgroundOffset[layer] = gameHeight +backgroundTextureRegion[layer].getRegionHeight();
        }
        //player
        player = new Reimu(gameWidth, gameHeight, gameWidth /20, gameHeight /16);
        //item
        itemAtlas = new TextureAtlas("Atlas/Itemdrops.atlas");
        //enemies
        fairiesAtlas = new TextureAtlas("Atlas/Fairies.atlas");
        bulletAtlas = new TextureAtlas("Atlas/Bullets.atlas");
        //create mob waves
        createF0A1Waves();
        //boss
        boss = new FakeBoss(gameHeight*0.7f,gameWidth*0.25f,gameHeight*0.1f,gameWidth*0.5f - (gameWidth*0.25f)/2,gameHeight+gameHeight*0.2f,fairiesAtlas,bulletAtlas);
        SPowerUp.sPowerUpLinkedList = new LinkedList<>();
        BPoint.bPointLinkedList = new LinkedList<>();
        batch = new SpriteBatch();
        hud = new HUDScreen(batch,viewport);
        pauseScreen = new PauseScreen(batch);
        player.setSpeed(gameHeight/2);
        Core.shootSE = Gdx.audio.newSound(Gdx.files.internal("SE/shotA.wav"));
        Core.bgm = Gdx.audio.newMusic(Gdx.files.internal("bgm/th06_02.wav"));
        Core.setStageStatus(Core.GAME_RESUME);
        pauseScreen.setVisible(false);
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(this);
        multiplexer.addProcessor(pauseScreen.stage);
        Gdx.input.setInputProcessor(multiplexer);
    }


    @Override
    public void show() {
        Core.bgm.play();
        Core.bgm.setVolume(Core.VOLUME);
    }

    @Override
    public void render(float delta) {
        elapseTime += delta;
        camera.update();
        if(Core.bgm != null && Core.bgm.isPlaying() && Core.bgm.getPosition() >= 86.0f){
            Core.bgm.setPosition(21.0f);
        }
        switch (GAME_STATUS){
            case Core.GAME_RESUME:{
                batch.begin();
                // draw everything here
                // background
                renderBackgound(delta);
                //player
                player.drawAnimation(batch, elapseTime);
                //draw player bullet and others
                player.drawAmulet(delta, batch, gameWidth, gameHeight);
                player.draw(batch, elapseTime);

                //spawnBoss
                boss.draw(batch);
                // end drawing
                //update game
                //player
                player.update(delta); // update player fire data
                //touch control
                player.inputControl(delta, gameWidth, gameHeight, viewport);
                //mobs
                updateWaves(delta);  // countdown to spawn
                updateEnemy(delta); // control mobs bullet and movement
                //boss
                boss.update(delta); // control boss movement
                boss.renderBullet(delta, batch, gameWidth, gameHeight); // control boss shooting phase
                getClosest(delta);// get closest mob/boss to player
                //item
                SPowerUp.renderPowerup(batch, delta, gameWidth, gameHeight); // create buffs on kill
                BPoint.renderBPoint(batch, delta, gameWidth, gameHeight); // create buffs on kill
                //detect collisions
                detectCollisions();
                hud.update(score, player.lives);
                batch.end();
                batch.setProjectionMatrix(camera.combined);
                hud.stage.act(delta);
                hud.stage.draw();
                pauseScreen.setVisible(false);
            }break;
            case Core.GAME_PAUSE:{
                pauseScreen.setVisible(true);
                pauseScreen.stage.act(delta);
                pauseScreen.stage.draw();
            }break;
        }
        isGameOver();
    }

    private void isGameOver() {
        if(GAME_STATUS == Core.END_STAGE || GAME_STATUS == Core.GAME_OVER){
            pause();
            Core.Point = score;
            parent.callbackListener.startActivity(Core.Point);
            parent.changeScreen(Core.GAME_EXIT);
            dispose();
        }
        if(boss.currentState == Boss.IS_DESTROY){
            GAME_STATUS = Core.END_STAGE;
        }
        if(player.lives  == 0){
            GAME_STATUS = Core.GAME_OVER;
        }
    }

    public void createF0A1Waves(){
        LinkedList<ArrayList<Float>> f0A1Wave = LoadXmlDoc.getF0A1Wave("gameData/Stage1.xml");
        LinkedList<ArrayList<Float>> f0A1WaveInfo = LoadXmlDoc.getF0A1Info("gameData/Stage1.xml");
//        ListIterator<ArrayList<Float>> waveIterator = f0A1Wave.listIterator();
//        ListIterator<ArrayList<Float>> waveInfoIterator = f0A1WaveInfo.listIterator();
        totalF0A1Wave = f0A1Wave.size();
        timeBettweenSpawnF0A1 = new float[totalF0A1Wave];
        timeBetweenWaveF0A1 = new float[totalF0A1Wave];
        numPerSpawn = new float[totalF0A1Wave];
        totalNumPerWave = new float[totalF0A1Wave];
        int sum = 0;
        for(int i = 0; i < totalF0A1Wave; i++){
            timeBettweenSpawnF0A1[i] = f0A1Wave.get(i).get(0);
            timeBetweenWaveF0A1[i] = f0A1Wave.get(i).get(1);
            numPerSpawn[i] = f0A1Wave.get(i).get(2);
            totalNumPerWave[i] = f0A1Wave.get(i).get(3);
            sum += totalNumPerWave[i];
        }

        f0A1Array = new F0A1[sum];
        int arraycount = 0;
        for (int i = 0; i < totalF0A1Wave; i++){
            for (int j = 0; j < f0A1WaveInfo.get(i).size(); j++){
                // create new mob
                f0A1Array[arraycount] = new F0A1(gameHeight* f0A1WaveInfo.get(i).get(j++),
                        gameWidth* f0A1WaveInfo.get(i).get(j++),gameHeight-gameWidth* f0A1WaveInfo.get(i).get(j),
                        gameWidth* f0A1WaveInfo.get(i).get(j++),gameHeight* f0A1WaveInfo.get(i).get(j++),
                        fairiesAtlas,bulletAtlas, f0A1WaveInfo.get(i).get(j++));
                f0A1Array[arraycount].timeBetweenShoot = f0A1WaveInfo.get(i).get(j++);

                // add movement control to mobs
                switch (i){
                    case 0:
                        f0A1Array[arraycount].exitRoute = F0A1.UP;break;
                    case 1: if(arraycount%2 == 0) {
                        f0A1Array[arraycount].exitRoute = F0A1.LEFT;break;
                    }
                        f0A1Array[arraycount].exitRoute = F0A1.RIGHT;break;
                    case 2: if(arraycount%2 != 0){
                        f0A1Array[arraycount].exitRoute = F0A1.LEFT;break;
                    }
                        f0A1Array[arraycount].exitRoute = F0A1.RIGHT;break;
                    case 3:
                        if(arraycount%2 == 0) {
                            f0A1Array[arraycount].exitRoute = F0A1.DOWNLEFT;break;
                        }
                        f0A1Array[arraycount].exitRoute = F0A1.DOWNRIGHT;break;
                    case 4:
                        if(arraycount%2 != 0){
                            f0A1Array[arraycount].exitRoute = F0A1.DOWNLEFT;break;
                        }
                        f0A1Array[arraycount].exitRoute = F0A1.DOWNRIGHT;break;
                    default:
                        f0A1Array[arraycount].exitRoute = F0A1.RIGHT;break;
                }
                // add time till despawn/exit
                f0A1Array[arraycount++].ttl = f0A1WaveInfo.get(i).get(j);
            }
        }
    }

    private void updateWaves(float deltaTime) {
        //wait for rapid spawn
        //check wave cooldown
        if(waveF0A1Counter < totalF0A1Wave){
            if((timeBetweenWaveF0A1[waveF0A1Counter] -= deltaTime) <= 0){
                //check if wave is done
                if(totalNumPerWave[waveF0A1Counter] > 0){
                    //wait for spawn cooldown
                    if((spawnTimeF0A1 += deltaTime) >= timeBettweenSpawnF0A1[waveF0A1Counter]){
                        if(f0A1Array[currentF0A1spawn].currentState == F0A1.NOT_SPAWN){
                            for (int i = 0; i < numPerSpawn[waveF0A1Counter]; i++){
                                f0A1Array[currentF0A1spawn].draw(batch);
                                f0A1Array[currentF0A1spawn++].currentState = F0A1.IS_SPAWNED;
                                spawnTimeF0A1 -= timeBettweenSpawnF0A1[waveF0A1Counter];
                                totalNumPerWave[waveF0A1Counter]--;
                            }
                            if(totalNumPerWave[waveF0A1Counter] == 0)
                                waveF0A1Counter++;
                        }
                    }//spawn cooldown
                }//end wave spawn
            }//wave cooldown
            return;
        }
        if(waveF0A1Counter == totalF0A1Wave){
            if((boss.waitTime+=deltaTime) >= boss.timeTillSpawn){
                boss.currentState = Boss.IS_SPAWNED;
                waveF0A1Counter++;
            }
        }
    }

    private void getClosest(float deltaTime){
        // get distance to closest spawned
        float shortdst = gameHeight;
        Rectangle target = new Rectangle();
        for(int i = 0;i < f0A1Array.length;i++){
            if(f0A1Array[i].currentState == Fairies.IS_SPAWNED){
                if(shortdst > player.centerPlayerVector.dst(f0A1Array[i].centerFairesVector)){
                    shortdst = player.centerPlayerVector.dst(f0A1Array[i].centerFairesVector);
                    target = f0A1Array[i].boundingBox;
                }
            }
        }
        if(boss.currentState == Boss.IS_SPAWNED){
            if(shortdst > player.centerPlayerVector.dst(boss.centerBossVector)){
                target = boss.boundingBox;
            }
        }
        player.specialAmuletUpdate(target,deltaTime);
    }

    private void updateEnemy(float deltaTime){
        for (F0A1 _this: f0A1Array) {
            //is spawned
            if(_this.currentState == F0A1.IS_SPAWNED){
                _this.update(deltaTime);
                _this.draw(batch);
            }
            _this.renderF0A1Bullet(batch,deltaTime,gameHeight,gameWidth);
        }
    }

    private void renderBackgound(float deltaTime) {
        if(GAME_STATUS == Core.GAME_RESUME){
            backgroundOffset[0] -= deltaTime * backgroundMaxScrollSpeed / 2;
            backgroundOffset[1] -= deltaTime * backgroundMaxScrollSpeed / 2;
            backgroundOffset[2] -= deltaTime * backgroundMaxScrollSpeed / 4;
            backgroundOffset[3] -= deltaTime * backgroundMaxScrollSpeed / 5;
        }
        for (int layer = 0; layer < backgroundOffset.length;layer++){
            switch (layer){
                case 0:
                case 1:
                    if (backgroundOffset[layer] < -gameHeight-backgroundTextureRegion[layer].getRegionHeight()){
                        backgroundOffset[layer] = gameHeight;
                    }
                    batch.draw(backgroundTextureRegion[layer],
                            backgroundX[layer],
                            backgroundOffset[layer],
                            gameWidth+backgroundTextureRegion[layer].getRegionWidth(),
                            gameHeight+backgroundTextureRegion[layer].getRegionHeight());
                    break;
                default:
                    if(backgroundOffset[layer] < -backgroundTextureRegion[layer].getRegionHeight()*3){
                        backgroundOffset[layer] = gameHeight +backgroundTextureRegion[layer].getRegionHeight();
                        backgroundX[layer] = generator.nextInt((int)gameWidth);
                    }
                    batch.draw(backgroundTextureRegion[layer],backgroundX[layer], backgroundOffset[layer],
                            gameWidth /6, gameHeight /6);
                    break;
            }
        }
    }

    private void detectCollisions(){
        ListIterator<Bullet> iterator = player.characterBulletList.listIterator();
        // character hit something
        while (iterator.hasNext()){
            Bullet bullet = iterator.next();
            for (F0A1 f0a1: f0A1Array)
                //when atk hit
                //check if spawn yet
                if(f0a1.currentState == F0A1.IS_SPAWNED) {
                    if(f0a1.intersects(bullet.boundingBox)){
                        try{
                            iterator.remove();
                        }catch (Exception e){}
                        if ((f0a1.hp -= player.normalAmuletDmg) <= 0) {
                            f0a1.currentState = F0A1.IS_DESTROY;
                            score += 100;
                            createSPowerUp(f0a1.boundingBox.x, f0a1.boundingBox.y, f0a1.boundingBox.width);
                            createBPoint(f0a1.boundingBox.x, f0a1.boundingBox.y, f0a1.boundingBox.width);
                        }
                    }
                }
            if(boss.currentState == Boss.IS_SPAWNED && boss.immortalFrame == 0 && boss.intersects(bullet.boundingBox)){
                try{
                    iterator.remove();
                }catch (Exception e){}
                score += 100;
                if((boss.hp -= player.normalAmuletDmg) <=0 ){
                    score += 900;
                    boss.currentState = Boss.IS_DESTROY;
                }
            }
        }
        iterator = player.characterSpecialBullet.listIterator();
        // character hit something
        while (iterator.hasNext()){
            Bullet bullet = iterator.next();
            for (F0A1 f0a1: f0A1Array)
                //when atk hit
                //check if spawn yet
                if(f0a1.currentState == F0A1.IS_SPAWNED && f0a1.intersects(bullet.boundingBox)) {
                    try{
                        iterator.remove();
                    }catch(Exception e){}
                    if ((f0a1.hp -= player.normalAmuletDmg) <= 0) {
                        f0a1.currentState = F0A1.IS_DESTROY;
                        score += 100;
                        createSPowerUp(f0a1.boundingBox.x, f0a1.boundingBox.y, f0a1.boundingBox.width);
                        createBPoint(f0a1.boundingBox.x, f0a1.boundingBox.y, f0a1.boundingBox.width);
                    }
                }
            if(boss.currentState == Boss.IS_SPAWNED && boss.immortalFrame == 0 && boss.intersects(bullet.boundingBox)){
                try{
                    iterator.remove();
                }catch (Exception e){}
                score += 100;
                if((boss.hp -= player.normalAmuletDmg) <=0 ){
                    score += 900;
                    boss.currentState = Boss.IS_DESTROY;
                }
            }
        }
        //foreach enemy atk hit
        for (F0A1 f0A1: f0A1Array){
            iterator = f0A1.F0A1BulletList.listIterator();
            while (iterator.hasNext()) {
                Bullet bullet = iterator.next();
                if(player.intersectsHitbox(bullet.getBoundingBox()) && player.imoFrame < 0){
                    //its a hit
                    try{
                        iterator.remove();
                    }catch (Exception e){}
                    player.lives--;
                    player.power -= 10;
                    player.imoFrame = 3.0f;
                    player.powerLevel = player.powerLevel/10;
                }
            }
        }
        iterator = boss.bossBulletList.listIterator();
        while (iterator.hasNext()){
            Bullet bullet = iterator.next();
            if(player.intersectsHitbox(bullet.getBoundingBox()) && player.imoFrame < 0){
                //its a hit
                try{
                    iterator.remove();
                }catch (Exception e){}
                player.lives--;
                player.power -= 10;
                player.imoFrame = 3.0f;
                player.powerLevel = player.powerLevel/10;
            }
        }
        //when collect item
        ListIterator<SPowerUp> sPowerUpListIterator = SPowerUp.sPowerUpLinkedList.listIterator();
        while (sPowerUpListIterator.hasNext()) {
            SPowerUp sPowerUp = sPowerUpListIterator.next();
            if(player.intersectsCollect(sPowerUp.boundingBox)){
                player.addPower(SPowerUp.value);
                try{
                    sPowerUpListIterator.remove();
                }catch (Exception e){}
            }
        }
        ListIterator<BPoint> bPointListIterator = BPoint.bPointLinkedList.listIterator();
        while ((bPointListIterator.hasNext())){
            BPoint bPoint = bPointListIterator.next();
            if(player.intersectsCollect(bPoint.boundingBox)){
                score += BPoint.value;
                try{
                    bPointListIterator.remove();
                }catch (Exception e){}
            }
        }
    }

    private void createBPoint(float xPos, float yPos,float objectWidth) {
        if((generator.nextInt(10)+1)%2 == 0){
            int maxDrop = generator.nextInt(5)+1;
            for (int i = 0;i < maxDrop; i++){
                BPoint bPoint = new BPoint(generator.nextInt((int) objectWidth)+xPos,yPos,gameWidth*0.05f,gameWidth*0.05f,itemAtlas);
                bPoint.speed = gameHeight*0.05f;
                BPoint.bPointLinkedList.add(bPoint);
            }
        }
    }

    private void createSPowerUp(float xPos, float yPos,float objectWidth){
        if((generator.nextInt(10)+1)%3 == 0){
            SPowerUp sPowerUp = new SPowerUp(generator.nextInt((int) objectWidth) +xPos,yPos,gameWidth*0.05f,gameWidth*0.05f,itemAtlas);
            sPowerUp.speed = gameHeight*0.05f;
            SPowerUp.sPowerUpLinkedList.add(sPowerUp);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);
        hud.stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {
        GAME_STATUS = Core.GAME_PAUSE;
    }

    @Override
    public void resume() {
        GAME_STATUS = Core.GAME_RESUME;
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        hud.dispose();
        pauseScreen.dispose();
        Core.shootSE.dispose();
        Core.bgm.dispose();
        itemAtlas.dispose();
        bulletAtlas.dispose();
        fairiesAtlas.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)){
            if(GAME_STATUS == Core.GAME_PAUSE){
                System.out.println("resume");
                Core.setStageStatus(Core.GAME_RESUME);
                return false;
            }
            if(GAME_STATUS == Core.GAME_RESUME) {
                System.out.println("pause");
                Core.setStageStatus(Core.GAME_PAUSE);
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}

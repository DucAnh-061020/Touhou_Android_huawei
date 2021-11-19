package com.touhou.game.Camera;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.XmlReader;

import java.util.ArrayList;
import java.util.LinkedList;


public class LoadXmlDoc {
    public static ArrayList<ArrayList<String>> getBulletInfo(String filepath) {
        FileHandle screenXML = Gdx.files.internal(filepath);

        XmlReader xml = new XmlReader();
        XmlReader.Element element = xml.parse(screenXML);
        ArrayList<ArrayList<String>> bulletinfo = new ArrayList<ArrayList<String>>();
        ArrayList<String> bullet;
        for(int i = 0;i < element.getChildCount();i++){
            bullet = new ArrayList<>();
            bullet.add(element.getChild(i).getAttribute("id"));
            for (int j = 0; j < element.getChild(i).getChildCount(); j++){
                bullet.add(element.getChild(i).getChild(j).getText());
            }
            bulletinfo.add(bullet);
        }
        return bulletinfo;
    }
    public static LinkedList<ArrayList<Float>> getF0A1Wave(String filepath) {
        FileHandle screenXML = Gdx.files.internal(filepath);

        XmlReader xmlReader = new XmlReader();
        XmlReader.Element element = xmlReader.parse(screenXML);
        LinkedList<ArrayList<Float>> F0A1List = new LinkedList<>();
        ArrayList<Float> details;
        for (int i=0;i < element.getChildCount();i++){
            details = new ArrayList<>();
            details.add(element.getChild(i).getFloatAttribute("timebetweenspawn"));
            details.add(element.getChild(i).getFloatAttribute("timebetweenwave"));
            details.add(element.getChild(i).getFloatAttribute("numberperspawn"));
            details.add((float) element.getChild(i).getChildCount());

            F0A1List.add(details);
        }
        return F0A1List;
    }

    public static LinkedList<ArrayList<Float>> getF0A1Info(String filepath){
        FileHandle screenXML = Gdx.files.internal(filepath);

        XmlReader xmlReader = new XmlReader();
        XmlReader.Element element = xmlReader.parse(screenXML);
        LinkedList<ArrayList<Float>> F0A1List = new LinkedList<>();
        ArrayList<Float> details;
        for (int i=0;i < element.getChildCount();i++) {
            details = new ArrayList<>();
            for (int j=0;j < element.getChild(i).getChildCount();j++){
                details.add(element.getChild(i).getChild(j).getFloatAttribute("speed"));
                details.add(element.getChild(i).getChild(j).getFloatAttribute("spawnX"));
                details.add(element.getChild(i).getChild(j).getFloatAttribute("maxbullet"));
                details.add(element.getChild(i).getChild(j).getFloatAttribute("timebetweenshoot"));
                details.add(element.getChild(i).getChild(j).getFloatAttribute("exittime"));
            }
            F0A1List.add(details);
        }
        return F0A1List;
    }
}
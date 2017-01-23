/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wayerless;

import background.homeView;
import background.settingView;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

/**
 *
 * @author Faiz Rahiemy
 */
public class Wayerless implements ActionListener{

    IOFile file = new IOFile();
    homeView home;
    settingView setting;
    int window = 0;
    Image i;
    String start = "netsh wlan start hostednetwork";
    String stop = "netsh wlan stop hostednetwork";
    String name = "FRAHIEMY";
    String pass = "ayamgoreng";
    String hosted = "netsh wlan set hostednetwork mode=allow ssid="+name+" key="+pass;
    
    public static void main(String[] args) throws IOException{
        Wayerless w = new Wayerless();
        w.load();
        w.hosted();
        w.home();
    }
    
    public void load() throws IOException{
        Object oName = file.loadFile("name.txt");
        Object oPass = file.loadFile("pass.txt");
        if (oName == null){
            name = "FRAHIEMY";
        }else{
            name = (String) oName;
        }
        if (oPass == null){
            pass = "ayamgoreng";
        }else{
            pass = (String) oPass;
        }
    }
    
    public void home() throws IOException{
        window = 0;
        home = new homeView();
        status2();
        status();
        i = ImageIO.read(getClass().getResource("/background/icon.png"));
        home.setIconImage(i);
        home.getBtnSetting().addActionListener(this);
        home.getBtnStart().addActionListener(this);
        home.getBtnStop().addActionListener(this);
        home.setVisible(true);
    }
    
    public void setting() throws IOException{
        window = 1;
        setting = new settingView();
        i = ImageIO.read(getClass().getResource("/background/icon.png"));
        setting.setIconImage(i);
        setting.getBtnSetting().addActionListener(this);
        setting.getBtnSave().addActionListener(this);
        setting.getTfName().setText(name);
        setting.getTfPassword().setText(pass);
        setting.setVisible(true);
    }
    
    public void hosted() throws IOException{
        ProcessBuilder builder = new ProcessBuilder(
            "cmd.exe", "/c", hosted);
        builder.redirectErrorStream(true);
        Process p = builder.start();
    }
    
    public void start() throws IOException{
        ProcessBuilder builder = new ProcessBuilder(
            "cmd.exe", "/c", start);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        status2();
        status();
    }
    
    public void stop() throws IOException{
        ProcessBuilder builder = new ProcessBuilder(
            "cmd.exe", "/c", stop);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        status2();
        status();
    }
    
    public void status() throws IOException{
        ProcessBuilder builder = new ProcessBuilder(
            "cmd.exe", "/c", "netsh wlan show hostednetwork | findstr /V 255");
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
            line = r.readLine();
            if (line == null) { break; }
            System.out.println(line);
            home.getStatus().append(line+"\n");
        }
    }
    
    public void status2() throws IOException{
        ProcessBuilder builder = new ProcessBuilder(
            "cmd.exe", "/c", "netsh wlan show hostednetwork | findstr -i ssid");
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        home.getStatus().setText("");
        while (true) {
            line = r.readLine();
            if (line == null) { break; }
            System.out.println(line);
            home.getStatus().append(line+"\n");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object x = e.getSource();
        if (window == 0){
            if (x.equals(home.getBtnStart())){
                try {
                    start();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (x.equals(home.getBtnStop())){
                try {
                    stop();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (x.equals(home.getBtnSetting())){
                try {
                    setting();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                home.dispose();
            }
        }else{
            if (x.equals(setting.getBtnSave())){
                try {
                    name = setting.getTfName().getText();
                    pass = setting.getTfPassword().getText();
                    hosted();
                    file.saveFile(setting.getTfName().getText(), "name.txt");
                    file.saveFile(setting.getTfPassword().getText(), "pass.txt");
                    home();
                    setting.dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (x.equals(setting.getBtnSetting())){
                try {
                    home();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                setting.dispose();
            }
        }
    }  
}

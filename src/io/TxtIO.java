package io;

import model.blocks.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TxtIO {
    public void save_in_txt(ArrayList<BaseBlockModel> array, File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        for (BaseBlockModel cb : array) {
            if (cb.getId() != -1) {
                fileWriter.write(cb.toString());
            }
        }
        fileWriter.flush();
        fileWriter.close();
    }

    public HashMap<Integer, BaseBlockModel> loadFromTxt(File file) throws IOException {
        ArrayList<BaseBlockModel> arrayList = new ArrayList<>();
        ArrayList<String> headers = new ArrayList<>();
        String line;
        int tmp = 1;
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        line = bufferedReader.readLine();
        while (line != null) {
            headers.add(line);
            if (line.equals("--------")) {
                switch (headers.get(0)) {
                    case "type:Block" -> arrayList.add(new BlockModel(0));
                    case "type:Display" -> arrayList.add(new DisplayModel(0));
                    case "type:Start" -> arrayList.add(new StartModel(0));
                    case "type:End" -> arrayList.add(new EndModel(0));
                    case "type:InputOutput" -> arrayList.add(new InputOutputModel(0));
                    case "type:Cycle" -> arrayList.add(new CycleModel(0));
                    case "type:Function" -> arrayList.add(new FunctionModel(0));
                    case "type:Condition" -> arrayList.add(new ConditionModel(0));
                }
                if(!headers.get(tmp).substring(0, headers.get(tmp).indexOf(":") + 1).equals("xCord:")) {
                    while (!headers.get(tmp).substring(0, headers.get(tmp).indexOf(":") + 1).equals("xCord:")) {
                        sb.append(headers.get(tmp)).append('\n');
                        tmp += 1;
                    }
                }
                else {
                    sb.append(headers.get(1));
                    tmp = 2;
                }
                arrayList.get(arrayList.size() - 1).setText(sb.substring(sb.toString().indexOf(":") + 1));

                arrayList.get(arrayList.size() - 1).setCord(Integer.parseInt(headers.get(tmp).substring(headers.get(tmp).indexOf(':') + 1)),
                        Integer.parseInt(headers.get(tmp+1).substring(headers.get(tmp+1).indexOf(':') + 1)));

                arrayList.get(arrayList.size() - 1).setHW(Integer.parseInt(headers.get(tmp+2).substring(headers.get(tmp+2).indexOf(':') + 1)),
                        Integer.parseInt(headers.get(tmp+3).substring(headers.get(tmp+3).indexOf(':') + 1)));

                arrayList.get(arrayList.size() - 1).setId(Integer.parseInt(headers.get(tmp+4).substring(headers.get(tmp+4).indexOf(':') + 1)));
                if(arrayList.get(arrayList.size() - 1).getType().equals("Condition")) {
                    ConditionModel cm = (ConditionModel) arrayList.get(arrayList.size() - 1);
                    cm.setYesID(Integer.parseInt(headers.get(tmp+5).substring(headers.get(tmp+5).indexOf(':') + 1)));
                    cm.setNoID(Integer.parseInt(headers.get(tmp+6).substring(headers.get(tmp+6).indexOf(':') + 1)));
                    arrayList.set(arrayList.size() - 1, cm);
                } else {
                    for (int i = 1; i < headers.get(tmp+5).split(" ").length; i++) {
                        arrayList.get(arrayList.size() - 1).setConnection(Integer.parseInt(headers.get(tmp+5).split(" ")[i]));
                    }
                }
                headers = new ArrayList<>();
            }
            line = bufferedReader.readLine();
            sb = new StringBuilder();
        }
        HashMap<Integer, BaseBlockModel> hashMap = new HashMap<>();
        for (BaseBlockModel baseBlockModel : arrayList) {
            hashMap.put(baseBlockModel.getId(), baseBlockModel);
        }
        bufferedReader.close();
        return hashMap;
    }
}

package model.Check;

import model.blocks.*;

import java.util.ArrayList;
import java.util.Stack;

public class CheckDiagram {
    private int StartID = -1;
    private int EndID = -1;
    private int StartEndCounter = 0;
    private int current = 0;

    public String check(ArrayList<BaseBlockModel> array) {
        StringBuilder report = new StringBuilder();
        for (BaseBlockModel commonBlock : array) {
            if ((commonBlock.getType().equals("Start") | commonBlock.getType().equals("End")) & commonBlock.getId() != -1) {
                StartEndCounter += 1;
            }
        }
        for (BaseBlockModel commonBlock : array) {
            if (commonBlock.getType().equals("Start") & StartID == -1 & commonBlock.getId() != -1) {
                StartID = commonBlock.getId();
            }
            if (commonBlock.getType().equals("End") & EndID == -1 & commonBlock.getId() != -1) {
                EndID = commonBlock.getId();
            }
        }
        if(StartEndCounter == 2)
            report.append("Diagram have Start and End\n");
        else if(StartEndCounter == 1 | StartEndCounter == 0)
            report.append("Check the start and end blocks.\nMaybe one of these blocks is missing from the diagram\n");
        else
            report.append("Diagram contains more than one Start or End blocks.\nThe subsequent check will start from the first found starting and ending blocks\n");
        report.append("-------------------------\n");

        for (BaseBlockModel commonBlock : array) {
            if (commonBlock.getType().equals("Start")) {
                StartID = commonBlock.getId();
            }
            if (commonBlock.getType().equals("End")) {
                EndID = commonBlock.getId();
            }
        }
        if (StartID == -1 | EndID == -1) {
            return report.toString();
        } else {
            ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
            for (int i = 0; i < array.size(); i++) {
                lists.add(new ArrayList<>());
            }
            for (BaseBlockModel baseBlockModel : array) {
                if (baseBlockModel.getConnections() != null & baseBlockModel.getId() != -1)
                    lists.get(baseBlockModel.getId()).addAll(baseBlockModel.getConnections());
            }

            Stack<Integer> stack = new Stack<>();
            stack.push(StartID);

            ArrayList<Integer> visited = new ArrayList<>();
            visited.add(StartID);

            while (!stack.isEmpty()) {
                current = stack.pop();

                if (current == EndID) {
                    report.append("The diagram can be traversed from beginning to end - verified\n");
                    break;
                }

                for (int neighbor : lists.get(current)) {
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                        visited.add(neighbor);
                    }

                }
            }
            if (current != EndID)
                report.append("The diagram cant be traversed from beginning to end \n");

            for (BaseBlockModel commonBlock : array) {
                if(commonBlock.getId() != -1)
                    report.append(commonBlock.checkThisBlock());
            }

            return report.toString();
        }
    }
}
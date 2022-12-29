package fr.tartur.torchlines.commands.common;

public class TorchStage {

    private final int[][] content;
    private int index;

    public TorchStage() {
        this.content = new int[64][38];
        this.index = 0;
    }

    public boolean newLine(String line) {
        if (line.length() == 38) {
            for (int i = 0; i < 38; i++) {
                try {
                    int num = Integer.parseInt(String.valueOf(line.charAt(i)));

                    if (num == 0 || num == 1) {
                        this.content[index][i] = num;
                    } else {
                        return false;
                    }
                } catch (NumberFormatException exception) {
                    return false;
                }
            }

            this.index++;
            return true;
        }

        return false;
    }

    public boolean isFull() {
        return this.index == 64;
    }

    public String getContentAsString() {
        StringBuilder builder = new StringBuilder();

        for (int[] table : this.content) {
            builder.append("[ ");
            for (int num : table) {
                builder.append(num).append(' ');
            }
            builder.append("]\n");
        }

        return builder.toString();
    }

    public int[][] getContent() {
        return content;
    }
}

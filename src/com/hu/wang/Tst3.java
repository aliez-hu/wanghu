package com.hu.wang;

public class Tst3 {
    public static void main(String[] args) {
        String str = "attribute1`\n" +
                "attribute2`\n" +
                "attribute3`\n" +
                "attribute4`\n" +
                "attribute5`\n" +
                "attribute6`\n" +
                "attribute7`\n" +
                "attribute8`\n" +
                "attribute9`\n" +
                "attribute10\n" +
                "attribute11\n" +
                "attribute12\n" +
                "attribute13\n" +
                "attribute14\n" +
                "attribute15\n" +
                "attribute16\n" +
                "attribute17\n" +
                "attribute18\n" +
                "attribute19\n" +
                "attribute20\n" +
                "attribute21\n" +
                "attribute22\n" +
                "attribute23\n" +
                "attribute24\n" +
                "\n" +
                "attribute25";

        String[] strArray = str.split("\n");
        StringBuilder stringBuilder = new StringBuilder();
        for (String column : strArray){
            column = column.replace("`","").replace("\n","");
            //stringBuilder.append(column+",");
            stringBuilder.append("?,");
        }
        System.out.println(stringBuilder);

    }

}

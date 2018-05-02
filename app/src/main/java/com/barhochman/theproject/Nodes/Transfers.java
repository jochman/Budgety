package com.barhochman.theproject.Nodes;

public class Transfers {
    public Transfers(String name, double amount, String category) {
        this.name = name;
        this.category = category;
        this.amount = amount;
    }

    public String toString(){
        return "{\""+ NodeStrings.getName() +"\": \"" + name + "\",\n" +
               "\""+ NodeStrings.getCategory() +"\": \"" + category + "\",\n" +
               "\""+ NodeStrings.getAmount() +"\": " + amount + "}";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public static class NodeStrings{

        public static String getName() {
            return name;
        }

        public static String getCategory() {
            return category;
        }

        public static String getAmount() {
            return amount;
        }

        private static String name = "name";
        private static String category = "category";
        private static String amount = "amount";
    }

    private String name;
    private String category;
    private double amount;
}

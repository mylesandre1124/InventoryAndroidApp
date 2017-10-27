package com.mylesandre1124.inventoryandroid.exceptions;


public class ItemAlreadyScannedException extends Exception {

    private int count;
    private String scannerName;

    public ItemAlreadyScannedException(String scannerName, int count) {
        super(scannerName + " has already scanned this item in with a count of: " + count + ". ");
        this.scannerName = scannerName;
        this.count = count;
    }

    @Override
    public String toString() {
        return "ItemAlreadyScannedException{" +
                "count=" + count +
                ", scannerName='" + scannerName + '\'' +
                '}';
    }
}

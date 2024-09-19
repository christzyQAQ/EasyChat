package com.easychat.enums;

public enum PageSize {
    SIZE_10(10), SIZE_20(20), SIZE_30(30);
    int size;

    private PageSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}

package com.example.minkyu.a_team_diary.ViewItem;

public class FolerNameItem {
    String FolderName;
    int resId;

    public FolerNameItem(String folderName, int resId) {
        FolderName = folderName;
        this.resId = resId;
    }

    public String getFolderName() {
        return FolderName;
    }

    public void setFolderName(String folderName) {
        FolderName = folderName;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    @Override
    public String toString() {
        return "FolerNameItem{" +
                "FolderName='" + FolderName + '\'' +
                ", resId=" + resId +
                '}';
    }
}

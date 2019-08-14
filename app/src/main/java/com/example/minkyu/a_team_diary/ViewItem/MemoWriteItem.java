package com.example.minkyu.a_team_diary.ViewItem;

public class MemoWriteItem {

    String Memo;
    int Num;

    public int getNum() {
        return Num;
    }

    public void setNum(int num) {
        Num = num;
    }

    public String getMemo() {
        return Memo;
    }

    public void setMemo(String memo) {
        Memo = memo;
    }

    @Override
    public String toString() {
        return "MemoWriteItem{" +
                "Memo='" + Memo + '\'' +
                ", Num=" + Num +
                '}';
    }
}

package org.mintflow.simple.bean;

import java.util.List;

public class Buy {
    private List<Goods> goodsList;
    private People people;

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }
}

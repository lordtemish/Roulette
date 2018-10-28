package com.dynamica.lordtemich.roulette.Forms;

public class RouletteForm {
    int id;
    String name;
    int drawable, extraDrawable;
    boolean Alpha=false, clicked=true;
    public RouletteForm(int id, String name, int drawable, int ex){
        this.id=id;
        this.name=name;
        this.drawable=drawable;
        extraDrawable=ex;
    }
    public RouletteForm(RouletteForm form){
        this.id=form.getId();
        this.name=form.getName();
        this.drawable=form.getDrawable();
        extraDrawable=form.getExtraDrawable();
    }
    public RouletteForm(int id, String name, int drawable){
        this.id=id;
        this.name=name;
        this.drawable=drawable;
    }
    public RouletteForm(int id, String name){
        this.id=id;
        this.name=name;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setExtraDrawable(int extraDrawable) {
        this.extraDrawable = extraDrawable;
    }

    public int getExtraDrawable() {
        return extraDrawable;
    }

    public boolean isAlpha() {
        return Alpha;
    }

    public void setAlpha(boolean alpha) {
        Alpha = alpha;
    }

    public String getName() {
        return name;
    }

    public int getDrawable() {
        return drawable;
    }

    public int getId() {
        return id;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}

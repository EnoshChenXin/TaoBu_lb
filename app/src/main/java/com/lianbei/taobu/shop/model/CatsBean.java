package com.lianbei.taobu.shop.model;

public class CatsBean {

  private int level;
  private String cat_name;
  private String cat_key;
  private int cat_id;
  private int  parent_cat_id;
  private boolean isSelect;

    public String getCat_key() {
        return cat_key;
    }

    public void setCat_key(String cat_key) {
        this.cat_key = cat_key;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public int getParent_cat_id() {
        return parent_cat_id;
    }

    public void setParent_cat_id(int parent_cat_id) {
        this.parent_cat_id = parent_cat_id;
    }


}

package com.kyssion.galaxy.script.translater.symbol;

public enum GrammaType {
    a("namespaceId", "a", 0),
    b("processId", "b", 1),
    c("handleId", "c", 2),
    Z("SZ|#", "Z", 3),
    S("namespace(a){K}", "S", 4),
    K("process(b)P;K|#", "K", 5),
    P("->h:{c}P|#", "P", 6),
    ROOT("ROOT", "ROOT", 7),
    EMPLE("EMPLE", "EMP:E", 8);

    GrammaType(String rule, String name, int id) {
        this.rule = rule;
        this.name = name;
        this.id = id;
    }

    private String rule;
    private String name;
    private int id;

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

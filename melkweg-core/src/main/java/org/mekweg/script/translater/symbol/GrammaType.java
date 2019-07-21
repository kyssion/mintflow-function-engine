package org.mekweg.script.translater.symbol;

public enum GrammaType {
    a("namespaceId", "a", 0),
    b("processId", "b", 1),
    c("handleId", "c", 2),
    d("handleList", "d", 3),
    Z("SZ|#", "Z", 4),
    S("namespace(a){K}", "S", 5),
    K("process(b)P;K|#", "K", 6),
    P("->h(c)P|->if(c){P}Eel{P}|r(c){d}|#", "P", 7),
    E("->elif(c){P}E|#", "P", 7),
    ROOT("ROOT", "ROOT", 8),
    EMPLE("EMPLE", "EMP:E", 9),
    HAS_ERROR_EMPLE("HAS_ERROR_EMPLE", "EMP:E", 10);
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

package model.domain;

public enum Role {
    AMMINISTRATOR(0),
    NEGOZIO(1),
    UTENTE(2),
    NONE(3);

    private final int id;

    private Role(int id) {
        this.id = id;
    }

    public static Role fromint(int id) {
        for (Role ruolo : values()) {
            if (ruolo.ordinal()==id){
                return ruolo;
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

}

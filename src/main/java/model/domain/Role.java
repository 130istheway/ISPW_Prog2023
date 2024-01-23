package model.domain;

public enum Role {
    AMMINISTRATOR(1),
    NEGOZIO(2),
    UTENTE(3),
    NONE(4);

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

package testing;

import model.dao.login.DAOLoginFileSystem;
import model.domain.Credential;
import model.domain.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DAOLoginFileSystemTest {

    @Test
    void test(){
        DAOLoginFileSystem dao = new DAOLoginFileSystem();
        Credential cred = dao.execute("lollo", "lollo");
        assertEquals("lollo",cred.getUsername());
        assertEquals("lollo", cred.getPassword());
        assertEquals(Role.NEGOZIO, cred.getRole());
    }
}
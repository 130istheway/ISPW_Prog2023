package testing;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import util.MessageToCommand;
import util.PayloadToCredential;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Stefano
 */
class UtilTest {

    @Test
    void testPayloadToCredential(){
        String input = "user:abcd,pass:efgh";
        PayloadToCredential brigido = new PayloadToCredential();
        List<String> test = brigido.getCredentials(input);
        List<String> inputScritto = new ArrayList<>();
        inputScritto.add("abcd");
        inputScritto.add("efgh");
        assertEquals(inputScritto.get(0), test.get(0));
        assertEquals(inputScritto.get(1), test.get(1));
    }

    @Test
    void testMessageToCredential(){
        MessageToCommand message = new MessageToCommand();
        String input = "EXIT | sei sicuro";
        message.fromMessage(input);
        assertEquals("sei sicuro", message.getPayload());
        assertEquals("EXIT", message.getCommand());
        message.setCommand("SI");
        message.setPayload("sono veramente sicuro");
        assertEquals("SI | sono veramente sicuro", message.toMessage());
        message.setPayload("si|si|si");
        assertEquals("SI | si|si|si", message.toMessage());
        input = "INS | 1|2|3|4|5";
        message.fromMessage(input);
        assertEquals("1|2|3|4|5", message.getPayload());
    }

}

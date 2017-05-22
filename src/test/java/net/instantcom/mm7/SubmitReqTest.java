package net.instantcom.mm7;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link SubmitReq}
 * <p>
 * Created by bardug on 22/5/2017.
 */
public class SubmitReqTest {

    @Test
    public void submitReq1() throws IOException, MM7Error {
        String ct = "multipart/related; boundary=\"==MM7-SOAP==c884feef-05ab-451e-9595-9772f60eb633\"; type=text/xml";
        InputStream in = SubmitReq.class.getResourceAsStream("submit-req1.txt");
        SubmitReq submitReq = (SubmitReq) MM7Response.load(in, ct, new MM7Context());

        assertEquals("", submitReq.getVasId());
        assertEquals("PRLS01AUDC", submitReq.getVaspId());
        assertEquals("+13056024143", submitReq.getSenderAddress().getAddress());
        assertEquals("+12345678903", submitReq.getRecipients().get(0).getAddress());
        assertEquals("+13056024143", submitReq.getServiceCode());
        assertEquals(MessageClass.PERSONAL, submitReq.getMessageClass());
        assertEquals(ChargedParty.SENDER, submitReq.getChargedParty());
    }

}

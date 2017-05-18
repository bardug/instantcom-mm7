package net.instantcom.mm7;

/**
 * MMSC server interface to use this library also as a server side
 * <p>
 * Created by amib on 17/5/2017.
 */
public interface MMSCServer {

    SubmitRsp receive(SubmitReq submitReq) throws MM7Error;

}

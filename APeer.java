package jChat;

import java.io.IOException;
import java.net.Socket;

public interface APeer {

	void addSocket(Socket accept) throws IOException;

}

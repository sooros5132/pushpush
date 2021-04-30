package com.team.server;

import java.io.IOException;

public class ServerPorgram {

	public static void main(String[] args) throws IOException {
		GameServer server = new GameServer(3, 10005);
	}

}

package org.cheese.town.api;

import org.cheese.town.utils.MongoDB;

public class EconomyAPI {
    private final MongoDB mongoManager;

    public EconomyAPI() {
        this.mongoManager = MongoDB.getInstance();

        System.out.println("[main] UserAPI 인스턴스 생성됨.");
    }
}

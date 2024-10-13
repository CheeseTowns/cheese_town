package org.mascord.main.api;

import org.mascord.main.utils.MongoDB;

public class EconomyAPI {
    private final MongoDB mongoManager;

    public EconomyAPI() {
        this.mongoManager = MongoDB.getInstance();

        System.out.println("[main] UserAPI 인스턴스 생성됨.");
    }
}

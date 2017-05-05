package com.sad.sadcrm;

import java.net.ConnectException;

class ServerConnectException extends RuntimeException {
    ServerConnectException(ConnectException exception) {
        super(exception);
    }
}

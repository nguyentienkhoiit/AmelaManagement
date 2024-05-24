package com.khoinguyen.amela.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ErrorResponse {
    SC_NOT_FOUND(
            404,
            "/notFound",
            "404 Not Found",
            "The page you are looking for was not found."
    ),

    SC_METHOD_NOT_ALLOWED(
            405,
            "/methodNotAllowed",
            "405 Method Not Allow",
            "This method is not allowed for the requested URL"
    ),

    SC_INTERNAL_SERVER_ERROR(
            500,
            "/internalServerError",
            "500 Internal Server Error",
            "Sorry, something went wrong"
    ),

    SC_UNAUTHORIZED(
            401,
            "/unauthorized",
            "401 Authorized Require",
            "You are not allowed to access this resource"
    ),

    SC_FORBIDDEN(
            403,
            "/forbidden",
            "403 Forbidden",
            "Unfortunately, you do not have permission to view this page"
    ),

    SC_BAD_REQUEST(
            400,
            "/badRequest",
            "400 Bad Request",
            "Your browser sent a request that this server could not understand"
    ),

    SC_REQUESTS_TIMEOUT(
            400,
            "/requestsTimeout",
            "408 Request Timeout",
            "Your browser did not respond with the request timeout."
    ),

    SC_TOO_MANY_REQUESTS(
            429,
            "/tooManyRequests",
            "429 Too Many Requests",
            "You have send too many requests in a given amount of time."
    ),

    SC_BAD_GATEWAY(
            502,
            "/badGateway",
            "502 Bad Gateway",
            "The server returned an unexpected error."
    );

    int errorCode;
    String path;
    String title;
    String errorMessage;
}

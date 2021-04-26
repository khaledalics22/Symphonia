package com.example.symphonia.Service;

import com.example.symphonia.Helpers.TracksModel;
import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.HeaderMap;
import retrofit2.http.QueryMap;

public interface RetrofitApi {

    @DELETE("api/v1/me/following")
    Call<Void> unFollowArtists(
            @HeaderMap Map<String, String> headers,
            @QueryMap Map<String, String> params
    );
    //holds reset password API
    @PATCH("api/v1/users/resetpassword/{token}")
    Call<JsonObject> resetPassword(
            @Path("token") String token
            , @Body Map<String, String> passwords
    );

    //holds activate artist API
    @PATCH("api/v1/users/activate/{token}")
    Call<JsonObject> applyArtist(@Path("token") String token);


    @POST("api/v1/users/{id}/playlists")
    Call<JsonObject> createPlaylist(
            @HeaderMap Map<String, String> headers,
            @Path("id") String id,
            @Body Map<String, Object> body
    );

    @POST("api/v1/playlists/{playlist_id}/tracks")
    Call<Void> addTrackToPlaylist(
            @HeaderMap Map<String, String> headers,
            @Path("playlist_id") String id,
            @Body TracksModel body
    );

    @GET("api/v1/playlists/{playlist_id}/tracks")
    Call<JsonObject> getPlaylistTracks(
            @HeaderMap Map<String, String> headers,
            @Path("playlist_id") String id,
            @QueryMap Map<String, Object> params
    );

    @GET("api/v1/albums/{id}")
    Call<JsonObject> getAlbum(
            @Path("id") String id
    );

    @GET("api/v1/playlists/{id}")
    Call<JsonObject> getPlaylist(@Path("id") String id);

    @DELETE("api/v1/playlists/{id}")
    Call<Void> deletePlaylist(
            @HeaderMap Map<String, String> headers,
            @Path("id") String id
    );

    //holds apply premium API
    @POST("api/v1/me/apply-premium")
    Call<JsonObject> promotePrem(@HeaderMap Map<String, String> headers);

    //holds check premium token API
    @PATCH("api/v1/me/premium/{token}")
    Call<JsonObject> checkPremiumToken(@Path("token") String token);

    //holds sending device registration token API
    @PATCH("api/v1/me/registration-token")
    Call<JsonObject> sendRegisterToken(
            @HeaderMap Map<String, String> headers,
            @Body Map<String, String> body
    );

    //holds get notifications history API
    @GET("api/v1/me/notifications")
    Call<JsonObject> getNotifications(@HeaderMap Map<String, String> headers);

    //holds send facebook token API
    @POST("api/v1/users/auth/facebook/Symphonia")
    Call<JsonObject> loginFacebookAPI(@Body Map<String,String> body);

    //holds delete album API
    @DELETE("api/v1/albums/{album_id}")
    Call<JsonObject> deleteAlbum(
            @Path("album_id") String id,
            @HeaderMap Map<String, String> headers
    );

    //holds rename album API
    @PATCH("api/v1/albums/{album_id}")
    Call<JsonObject> renameAlbum(
            @Path("album_id") String id,
            @HeaderMap Map<String, String> headers,
            @Body Map<String, String> params
    );

    //holds edit profile API
    @PUT("api/v1/me/")
    Call<JsonObject>editProfile(
            @HeaderMap Map<String, String> headers,
            @Body Map<String,String>params
    );

    //holds delete track API
    @DELETE("api/v1/users/track/{track_id}")
    Call<JsonObject>deleteTrack(
            @Path("track_id") String id,
            @HeaderMap Map<String, String> headers
    );

    //holds rename track API
    @PATCH("api/v1/users/track/{track_id}")
    Call<JsonObject>renameTrack(
            @Path("track_id") String id,
            @HeaderMap Map<String, String> headers,
            @Body Map<String, String> params
    );

}

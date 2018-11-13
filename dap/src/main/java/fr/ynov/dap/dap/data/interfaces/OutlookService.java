package fr.ynov.dap.dap.data.interfaces;

import fr.ynov.dap.dap.data.microsoft.Message;
import fr.ynov.dap.dap.data.microsoft.OutlookUser;
import fr.ynov.dap.dap.data.microsoft.PagedResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OutlookService {

  @GET("/v1.0/me")
  Call<OutlookUser> getCurrentUser();

  @GET("/v1.0/me/mailfolders/{folderid}/messages")
  Call<PagedResult<Message>> getMessages(
    @Path("folderid") String folderId,
    @Query("$orderby") String orderBy,
    @Query("$select") String select,
    @Query("$top") Integer maxResults
  );
}

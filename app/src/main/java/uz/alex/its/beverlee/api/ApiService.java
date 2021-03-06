package uz.alex.its.beverlee.api;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import uz.alex.its.beverlee.model.requestParams.ChangePasswordParams;
import uz.alex.its.beverlee.model.requestParams.NotificationSettingsParams;
import uz.alex.its.beverlee.model.requestParams.UserDataParams;
import uz.alex.its.beverlee.model.actor.ContactModel;
import uz.alex.its.beverlee.model.CountryModel;
import uz.alex.its.beverlee.model.requestParams.TransferFundsParams;
import uz.alex.its.beverlee.model.requestParams.PinParams;
import uz.alex.its.beverlee.model.requestParams.RegisterParams;
import uz.alex.its.beverlee.model.requestParams.VerifyTransferParams;
import uz.alex.its.beverlee.model.requestParams.WithdrawalParams;
import uz.alex.its.beverlee.model.news.NewsDataModel;
import uz.alex.its.beverlee.model.news.NewsModel;
import uz.alex.its.beverlee.model.notification.NotificationSettingsModel;
import uz.alex.its.beverlee.model.transaction.TransactionModel;
import uz.alex.its.beverlee.model.actor.UserModel;
import uz.alex.its.beverlee.model.transaction.WithdrawalTypeModel;
import uz.alex.its.beverlee.model.balance.DaysBalance;
import uz.alex.its.beverlee.model.transaction.PurchaseModel.Purchase;
import uz.alex.its.beverlee.model.balance.Balance;
import uz.alex.its.beverlee.model.balance.MonthBalance;
import uz.alex.its.beverlee.model.LoginModel;
import uz.alex.its.beverlee.model.transaction.PurchaseModel;
import uz.alex.its.beverlee.model.requestParams.AuthParams;
import uz.alex.its.beverlee.model.requestParams.MakePurchaseParams;
import uz.alex.its.beverlee.model.requestParams.VerifyCodeParams;

public interface ApiService {

    /* Authentication */
    @POST("/api/auth/registration")
    Call<LoginModel> register(@Body final RegisterParams registerParams);

    @POST("/api/auth/login")
    Call<LoginModel> login(@Body final AuthParams authParams);

    @POST("/api/user/verify-code/sms")
    Call<Void> verifySms();

    @POST("/api/user/verify-code/call")
    Call<Void> verifyCall();

    @POST("/api/user/verify-code/verify")
    Call<Void> submitVerification(@Body final VerifyCodeParams verifyCodeParams);

    @GET("/api/user/verified")
    Call<Void> checkVerified();

    /* Pin-code */
    @GET("/api/user/pin")
    Call<Void> checkPinAssigned();

    @POST("/api/user/pin")
    Call<Void> assignPin(@Body final PinParams pinParams);

    @GET("/api/user/pin/verify")
    Call<Void> verifyPin(@Query("pin") final String pinCode);

    @POST("/api/user/pin/sms")
    Call<Void> changePinBySms();

    @POST("/api/user/pin/call")
    Call<Void> changePinByPhone();

    /* User data */
    @GET("/api/user")
    Call<UserModel> getUserData();

    @POST("/api/user")
    Call<UserModel> saveUserData(@Body final UserDataParams params);

    @Multipart
    @POST("/api/user/avatar")
    Call<Void> uploadAvatar(@Part final MultipartBody.Part avatarImageFile);

    @DELETE("/api/user/avatar")
    Call<Void> deleteAvatar();

    @POST("/api/user/password")
    Call<Void> changePassword(@Body final ChangePasswordParams params);

    /* Countries */
    @GET("/api/countries")
    Call<CountryModel> getCountryList();

    /* Transactions */
    /**
     * Registers the text to display in a tool tip.   The text
     * displays when the cursor lingers over the component.
     *
     * @param page          ?????????? ????????????????
     * @param perPage      ??????-???? ??????????????
     * @param typeId       ??????. 1 - ???????????????????? ????????????,
     *                      2 - ?????????????? ?? ????????????????,
     *                      3 - ?????????????? - ??????????????????,
     *                      4 - ?????????????? - ????????????????,
     *                      5 - ?????????????????? ?????????????? - ????????????????????,
     *                      6 - ?????????????????? ?????????????? - ??????????
     * @param dateStart    ???????? ????, ???????????? 2020-01-01
     * @param dateFinish ???????? ????, ???????????? 2020-01-31
     * @param contactId    ?????????????????????????? ???????????????????? ?????? ?????????????????????? ????????????????
     */
    @GET("/api/user/transactions")
    Call<TransactionModel> getTransactionHistory(@Query("page") final Integer page,
                                                 @Query("per_page") final Integer perPage,
                                                 @Query("type_id") final Integer typeId,
                                                 @Query("date_start") final String dateStart,
                                                 @Query("date_finish") final String dateFinish,
                                                 @Query("contact_id") final Long contactId);

    /* Balance */
    @GET("/api/user/balance/current")
    Call<Balance> getCurrentBalance();

    @POST("/api/user/balance/common")
    Call<MonthBalance> getMonthlyBalanceHistory(@Query("month") final int month);

    @POST("/api/user/balance/days")
    Call<DaysBalance> getMonthlyBalanceHistoryByDays(@Query("month") final int month);

    /* Purchases */
    @GET("/api/user/buy/requests")
    Call<List<Purchase>> getUserPurchases();

    @POST("/api/user/buy/requests/{requestId}/buy")
    Call<PurchaseModel> makePurchase(@Path("requestId") final long requestId, @Body MakePurchaseParams makePurchaseParams);

    @DELETE("/api/user/buy/requests/{requestId}")
    Call<Void> deletePurchase(@Path("requestId") final long requestId);

    /* Transfers */
    @POST("/api/user/transfer")
    Call<Balance> transferFunds(@Body final TransferFundsParams transferFundsParams);

    @POST("/api/user/transfer/verify")
    Call<Void> verifyTransfer(@Body final VerifyTransferParams verifyTransferParams);

    /* Withdrawal */
    @GET("/api/user/withdrawal/requests/types")
    Call<WithdrawalTypeModel> getWithdrawalTypes();

    @POST("/api/user/withdrawal/requests")
    Call<Void> withdrawFunds(@Body final WithdrawalParams withdrawalParams);

    /* News */
    @GET("/api/news")
    Call<NewsModel> getNews(@Query("page") final Integer page, @Query("per_page") final Integer perPage);

    @GET("/api/news")
    Call<NewsDataModel> getNewsData(@Query("newsId") final long newsId);

    /* Contacts */
    @GET("/api/user/contacts")
    Call<ContactModel> getContactList(@Query("search_query") final String searchQuery, @Query("page") final Integer page, @Query("per-page") final Integer perPage);

    @GET("/api/user/contacts")
    Call<ContactModel> getContactData(@Path("contactId") final long contactId);

    @DELETE("/api/user/contacts/{contactId}")
    Call<Void> deleteContact(@Path("contactId") final long contactId);

    /* Notifications */
    @GET("/api/user/notifications/settings")
    Call<NotificationSettingsModel> getNotificationSettings();

    @POST("/api/user/notifications/settings")
    Call<Void> saveNotificationSettings(@Body final NotificationSettingsParams params);
}


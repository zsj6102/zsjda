package com.colpencil.tenement.Api;

import com.colpencil.tenement.Bean.Advice;
import com.colpencil.tenement.Bean.AnFixBean;
import com.colpencil.tenement.Bean.Building;
import com.colpencil.tenement.Bean.CleanRecord;
import com.colpencil.tenement.Bean.EntityResult;
import com.colpencil.tenement.Bean.Equipment;
import com.colpencil.tenement.Bean.Feedback;
import com.colpencil.tenement.Bean.LastRecord;
import com.colpencil.tenement.Bean.ListCommonResult;
import com.colpencil.tenement.Bean.OnlineListUser;
import com.colpencil.tenement.Bean.OwnerRepair;
import com.colpencil.tenement.Bean.ReadingNum;
import com.colpencil.tenement.Bean.Record;
import com.colpencil.tenement.Bean.RepairOrderAssign;
import com.colpencil.tenement.Bean.Result;
import com.colpencil.tenement.Bean.ResultInfo;
import com.colpencil.tenement.Bean.ResultListInfo;
import com.colpencil.tenement.Bean.Room;
import com.colpencil.tenement.Bean.Sign;
import com.colpencil.tenement.Bean.SignInfo;
import com.colpencil.tenement.Bean.SignList;
import com.colpencil.tenement.Bean.TenementComp;
import com.colpencil.tenement.Bean.TodayTask.TodayTaskItemResult;
import com.colpencil.tenement.Bean.Unit;
import com.colpencil.tenement.Bean.UserInfo;
import com.colpencil.tenement.Bean.UtilitiesRecord;
import com.colpencil.tenement.Bean.VersionInfo;
import com.colpencil.tenement.Bean.Village;
import com.colpencil.tenement.Bean.Visitor;
import com.colpencil.tenement.Bean.WaterInfo;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * @author 汪 亮
 * @Description:
 * @Email DramaScript@outlook.com
 * @date 2016/9/13
 */
public interface HostApi {


    /**
     * 抄表记录
     *
     * @param id
     * @param page
     * @param pageSize
     * @return
     */
    @GET("meterRecord/recordList.do")
    Observable<ListCommonResult<Advice>> loadAdviceList(@Query("id") int id,
                                                        @Query("page") int page,
                                                        @Query("pageSize") int pageSize);

    /**
     * 获取投诉建议的列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GET("complaint/list.do")
    Observable<ListCommonResult<Advice>> loadAdvice(@Query("communityId") String communityId,
                                                    @Query("type") int type,
                                                    @Query("page") int page,
                                                    @Query("pageSize") int pageSize,
                                                    @Query("self") int self);

    /**
     * 登录
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("employee/login.do")
    Observable<EntityResult<UserInfo>> loginToServer(@FieldMap() HashMap<String, String> params);

    /**
     * 绿化保洁/巡检记录
     *
     * @param type
     * @param page
     * @param pageSize
     * @return
     */
    @GET("work/list.do")
    Observable<ListCommonResult<CleanRecord>> loadCleanRecord(@Query("communityId") String communityId,
                                                              @Query("type") int type,
                                                              @Query("page") int page,
                                                              @Query("pageSize") int pageSize,
                                                              @Query("beginTime") String beginTime,
                                                              @Query("endTime") String endTime,
                                                              @Query("self") int self);

    /**
     * 业主报修订单
     *
     * @param type
     * @param page
     * @param pageSize
     * @return
     */
    @GET("repairOrder/list.do")
    Observable<ListCommonResult<OwnerRepair>> loadRepairList(@Query("communityId") String communityId,
                                                             @Query("type") int type,
                                                             @Query("page") int page,
                                                             @Query("pageSize") int pageSize,
                                                             @Query("self") int self);

    /**
     * 设备管理
     *
     * @param type
     * @param page
     * @param pageSize
     * @return
     */
    @GET("maintain/list.do")
    Observable<ListCommonResult<Equipment>> loadEquipment(@Query("communityId") String communityId,
                                                          @Query("type") int type,
                                                          @Query("page") int page,
                                                          @Query("pageSize") int pageSize,
                                                          @Query("devCode") String devCode,
                                                          @Query("devName") String devName,
                                                          @Query("self") int self,
                                                          @Query("startDate") String startDate,
                                                          @Query("endDate") String endDate);

    /**
     * 访客管理
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GET("visit/visitorList.do")
    Observable<ListCommonResult<Visitor>> loadGuestList(@Query("communityId") String communityId,
                                                        @Query("page") int page,
                                                        @Query("pageSize") int pageSize);

    /**
     * 访客管理
     *
     * @return
     */
    @GET("employee/welcome.do")
    Observable<ListCommonResult<Village>> loadVillageList();

    /**
     * 报修指派
     * @param empId
     * @param orderId
     * @return
     */
    @GET("repairOrder/assign.do")
    Observable<RepairOrderAssign> loadEmployeeList(@Query("empId") int empId,
                                                   @Query("orderId") int orderId);

    /**
     * 投诉建议指派
     * @param empId
     * @param orderId
     * @return
     */
    @GET("complaint/assign.do")
    Observable<RepairOrderAssign> assignEmployee(@Query("empId") int empId,
                                                 @Query("orderId") int orderId);

    /**
     * 修改保洁/巡逻记录
     * @param workDetail
     * @param workId
     * @return
     */
    @GET("work/edit.do")
    Observable<Result> editRecord(@Query("workDetail") String workDetail,
                                  @Query("workId") int workId);

    /**
     * 获取楼宇
     *
     * @param communityId
     * @return
     */
    @GET("building/buildingList.do")
    Observable<ListCommonResult<Building>> loadBuilds(@Query("communityId") String communityId);

    /**
     *获取房间
     *
     * @param buildingId
     * @return
     */
    @GET("house/houseList.do")
    Observable<ListCommonResult<Room>> loadRooms(@Query("unitId") String buildingId);

    /**
     * 获取楼宇
     *
     * @param params
     * @return
     */
    @GET("meterRecord/recordList.do")
    Observable<ListCommonResult<UtilitiesRecord>> loadUtilitiesRecord(@QueryMap() HashMap<String, String> params);

    /**
     * 抄表数
     *
     * @return
     */
    @FormUrlEncoded
    @POST("meterRecord/recordCount.do")
    Observable<EntityResult<ReadingNum>> loadUtilitiesNum(@FieldMap() HashMap<String, String> params);

    /**
     * 获取上个月抄表数目
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("meterRecord/lastRecord.do")
    Observable<EntityResult<WaterInfo>> getLast(@FieldMap() HashMap<String, String> params);

    /**
     * 提交抄表记录
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("meterRecord/saveRecord.do")
    Observable<EntityResult<String>> submitWater(@FieldMap() HashMap<String, String> params);

    /**
     * 提交绿化保洁/巡检记录
     * @param params
     * @return
     */
    @Multipart
    @POST("work/save.do")
    Observable<EntityResult<String>> submitRecord(@PartMap() Map<String, RequestBody> params);

    /**
     * 获取上次巡检维修的信息
     *
     * @return
     */
    @GET("maintain/lastRecord.do")
    Observable<EntityResult<LastRecord>> getLastRecord(@Query("eqId") String eqId,
                                                       @Query("type") int type,
                                                       @Query("eqType") String eqType);

    /**
     * 设备维护记录提交
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("maintain/save.do")
    Observable<Result> postRecord(@FieldMap() HashMap<String, String> params);

    /**
     * 改变订单状态
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("repairOrder/stateChange.do")
    Observable<Result> changeState(@FieldMap() HashMap<String, String> params);

    /**
     * 改变投诉订单的状态
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("complaint/stateChange.do")
    Observable<Result> changeComplaintState(@FieldMap() HashMap<String, String> params);

    /**
     * 获取在线对讲的成员
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("employee/empList.do")
    Observable<ListCommonResult<OnlineListUser>> getOnline(@FieldMap() HashMap<String, String> params);

    /**
     * 今日任务
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("remind/list.do")
    Observable<TodayTaskItemResult> getTodayTask(@FieldMap() HashMap<String, String> params);

    /**
     * 获取签到状态
     * @return
     */
    @GET("sign/signStatus.do")
    Observable<EntityResult<SignInfo>> getSignState();

    /**
     * 签到
     * @param params
     * @return
     */
    @Multipart
    @POST("sign/signIn.do")
    Observable<EntityResult<Sign>> signIn(@PartMap() Map<String, RequestBody> params);

    /**
     * 签退
     * @param params
     * @return
     */
    @Multipart
    @POST("sign/signOut.do")
    Observable<EntityResult<Sign>> signOut(@PartMap() Map<String, RequestBody> params);

    /**
     * 签到/退列表接口
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("sign/signList.do")
    Observable<ListCommonResult<SignList>> getSignList(@FieldMap() HashMap<String, String> params);

    /**
     * 添加任务
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("remind/save.do")
    Observable<EntityResult> addTask(@FieldMap() HashMap<String, String> params);

    /**
     * 任务完成
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("remind/changeStatus.do")
    Observable<EntityResult> taskFinsh(@FieldMap() HashMap<String, String> params);

    /**
     * 获取物业公司列表
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("property/list.do")
    Observable<ListCommonResult<TenementComp>> getTenementCompList(@FieldMap() HashMap<String, String> params);

    /**
     * 修改密码
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("employee/changePassword.do")
    Observable<EntityResult<String>> changePwd(@FieldMap() HashMap<String, String> params);

    /**
     * 退出登录
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("employee/logout.do")
    Observable<EntityResult<String>> loginOut(@FieldMap() HashMap<String, String> params);

    /**
     * 查看反馈
     *
     * @param params
     * @return
     */
    @GET("feedbackiew.do")
    Observable<EntityResult<Feedback>> seeFeedback(@QueryMap HashMap<String, String> params);

    /**
     * 获取小区
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("community/list.do")
    Observable<ListCommonResult<Village>> getVillage(@FieldMap() HashMap<String, String> params);

    /**
     * 获取单元
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("unit/list.do")
    Observable<ListCommonResult<Unit>> getUnit(@FieldMap() HashMap<String, String> params);

    /**
     * 关联设备
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("repairOrder/linkDev.do")
    Observable<Result> linkDev(@FieldMap() HashMap<String, String> params);

    /**
     * 获取验证码
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("member/getValidCode.do")
    Observable<Result> getCode(@FieldMap() HashMap<String, String> params);

    /**
     * 添加备注
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("member/getValidCode.do")
    Observable<Result> submitBei(@FieldMap() HashMap<String, String> params);

    /**
     *  打点轨迹
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("work/route.do")
    Observable<ListCommonResult<Record>> getMarker(@FieldMap() HashMap<String, String> params);

    /**
     * 检查补丁操作
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("commons/checkUpdate.do")
    Observable<EntityResult<AnFixBean>> update(@FieldMap() HashMap<String, String> params);

    /**
     * 获取开门的钥匙串
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("openDoor/keys.do")
    Observable<ResultListInfo<String>> openKey(@FieldMap() HashMap<String, String> params);

    /**
     * 开门成功日志
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("openDoor/logging.do")
    Observable<ResultInfo> log(@FieldMap() HashMap<String, String> params);

    /**
     * 版本更新
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("commons/getCurrentVersion.do")
    Observable<ResultInfo<VersionInfo>> checkVer(@FieldMap() HashMap<String, String> params);



}

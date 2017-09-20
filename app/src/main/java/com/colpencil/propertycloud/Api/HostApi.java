package com.colpencil.propertycloud.Api;

import com.colpencil.propertycloud.Bean.Ad;
import com.colpencil.propertycloud.Bean.AddRegsit;
import com.colpencil.propertycloud.Bean.AdviceList;
import com.colpencil.propertycloud.Bean.AnFixBean;
import com.colpencil.propertycloud.Bean.Approve;
import com.colpencil.propertycloud.Bean.AreaInfo;
import com.colpencil.propertycloud.Bean.BalanceInfo;
import com.colpencil.propertycloud.Bean.Building;
import com.colpencil.propertycloud.Bean.Approveid;
import com.colpencil.propertycloud.Bean.CellInfo;
import com.colpencil.propertycloud.Bean.CellState;
import com.colpencil.propertycloud.Bean.CityBean;
import com.colpencil.propertycloud.Bean.CityInfo;
import com.colpencil.propertycloud.Bean.ComplaintHistroy;
import com.colpencil.propertycloud.Bean.ComplaintType;
import com.colpencil.propertycloud.Bean.CorfimPayFees;
import com.colpencil.propertycloud.Bean.CouponInfo;
import com.colpencil.propertycloud.Bean.DiviceInfo;
import com.colpencil.propertycloud.Bean.CurProgress;
import com.colpencil.propertycloud.Bean.EntityResult;
import com.colpencil.propertycloud.Bean.Feed;
import com.colpencil.propertycloud.Bean.FilterInfo;
import com.colpencil.propertycloud.Bean.FindGoodsId;
import com.colpencil.propertycloud.Bean.FitProcess;
import com.colpencil.propertycloud.Bean.FitmentInfo;
import com.colpencil.propertycloud.Bean.ModuleResult;
import com.colpencil.propertycloud.Bean.GoodsList;
import com.colpencil.propertycloud.Bean.HomeData;
import com.colpencil.propertycloud.Bean.HouseInfo;
import com.colpencil.propertycloud.Bean.IsExists;
import com.colpencil.propertycloud.Bean.ListAdvice;
import com.colpencil.propertycloud.Bean.ListBean;
import com.colpencil.propertycloud.Bean.LiveInfoList;
import com.colpencil.propertycloud.Bean.LoginInfo;
import com.colpencil.propertycloud.Bean.MenberInfo;
import com.colpencil.propertycloud.Bean.Mine;
import com.colpencil.propertycloud.Bean.OtherOrder;
import com.colpencil.propertycloud.Bean.PayFees;
import com.colpencil.propertycloud.Bean.PayInfo;
import com.colpencil.propertycloud.Bean.PayList;
import com.colpencil.propertycloud.Bean.Province;
import com.colpencil.propertycloud.Bean.RepairHistory;
import com.colpencil.propertycloud.Bean.ResultInfo;
import com.colpencil.propertycloud.Bean.ResultListInfo;
import com.colpencil.propertycloud.Bean.RoomInfo;
import com.colpencil.propertycloud.Bean.SelectFees;
import com.colpencil.propertycloud.Bean.TownInfo;
import com.colpencil.propertycloud.Bean.Transfer;
import com.colpencil.propertycloud.Bean.Unit;
import com.colpencil.propertycloud.Bean.VersionInfo;
import com.colpencil.propertycloud.Bean.WalletInfo;
import com.colpencil.propertycloud.Bean.orm.RegsitForm;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * @author 汪 亮
 * @Description: 项目中所有请求的API
 * @Email DramaScript@outlook.com
 * @date 2016/9/13
 */
public interface HostApi {

    /**
     * 登陆
     *
     * @return
     */
    @POST("member/login.do")
    @FormUrlEncoded
    Observable<ResultInfo<LoginInfo>> login(@FieldMap() HashMap<String, String> param);

    /**
     * 注册
     *
     * @return
     */
    @POST("member/register.do")
    @FormUrlEncoded
    Observable<ResultInfo> regist(@FieldMap() HashMap<String, String> params);

    /**
     * 获取验证码
     *
     * @param mobile
     * @param flag   1:注册、2:修改密码、3:忘记密码、5:修改联系电话
     * @return
     */
    @POST("member/getValidCode.do")
    @FormUrlEncoded
    Observable<ResultInfo> getCode(@Field("mobile") String mobile, @Field("flag") int flag, @Field("terminal") int terminal);

    /**
     * 退出登录
     *
     * @param comuId
     * @return
     */
    @POST("member/logout.do")
    @FormUrlEncoded
    Observable<ResultInfo> loginOut(@Field("comuId") String comuId);


    /**
     * 获取小区选择列表
     *
     * @param isProprietor
     * @param pageNo
     * @param pageSize
     * @return
     */
    @POST("member/getComuLst.do")
    @FormUrlEncoded
    Observable<ResultListInfo<CellInfo>> getSelectVilage(@Field("pageNo") int pageNo,
                                                         @Field("pageSize") int pageSize,
                                                         @Field("isProprietor") boolean isProprietor);

    /**
     * 记住默认小区
     *
     * @param memberId
     * @param comuId
     * @return
     */
    @POST("member/updateDefaultCommunity.do")
    @FormUrlEncoded
    Observable<ResultInfo<String>> selectVilage(@Field("memberId") String memberId,
                                                @Field("comuId") String comuId);

    /**
     * 缴费清单
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("homemaker/feesLst.do")
    Observable<ListBean<PayFees>> getPayFeesList(@FieldMap() HashMap<String, String> params);

    /**
     * 获取缴费清单的列表
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("homemaker/feesDetail.do")
    Observable<ListBean<PayList>> getPayFeesListDetail(@FieldMap() HashMap<String, String> params);

    /**
     * 我的历史投诉  / 报修
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("complaint/myComplaintList.do")
    Observable<ResultListInfo<ComplaintHistroy>> getCompHistoryList(@FieldMap() HashMap<String, String> params);

    /**
     * 投诉类型
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("complaint/complaintTypeList.do")
    Observable<ResultListInfo<ComplaintType>> getCompTypeList(@FieldMap() HashMap<String, String> params);

    /**
     * 提交投诉
     *
     * @param params
     * @return
     */
    @Multipart
    @POST("complaint/saveComplaint.do")
    Observable<ResultInfo> submitComplite(@PartMap() Map<String, RequestBody> params);

    /**
     * 获取我的资料
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("member/getMemberInfo.do")
    Observable<ResultInfo<Mine>> getMineInfo(@FieldMap() HashMap<String, String> params);

    /**
     * 更改资料
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("member/modMemberInfo.do")
    Observable<ResultInfo> changeInfo(@FieldMap() HashMap<String, String> params);

    /**
     * 更改头像
     *
     * @param params
     * @return
     */
    @Multipart
    @POST("member/modMemberInfo.do")
    Observable<ResultInfo> changeHead(@PartMap() Map<String, RequestBody> params);

    /**
     * 获取其他订单
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("homemaker/othrOrder.do")
    Observable<ListBean<OtherOrder>> getOtherOrder(@FieldMap() HashMap<String, String> params);

    /**
     * 提交报修
     *
     * @param params
     * @return
     */
    @Multipart
    @POST("repairOrder/saveRepairOrder.do")
    Observable<ResultInfo> submitRepairs(@PartMap() Map<String, RequestBody> params);

    /**
     * 获取历史报修
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("repairOrder/myRepairOrderList.do")
    Observable<ResultListInfo<RepairHistory>> getRepairHistory(@FieldMap() HashMap<String, String> params);

    /**
     * 提交意见反馈
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("repairOrder/feedBack.do")
    Observable<ResultInfo> submitFeed(@FieldMap() HashMap<String, String> params);

    /**
     * 获取意见反馈
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("repairOrder/getFeedBack.do")
    Observable<ResultListInfo<Feed>> getFeed(@FieldMap() HashMap<String, String> params);

    /**
     * 获取广告
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("homemaker/getAdv.do")
    Observable<ListBean<Ad>> getAd(@FieldMap() HashMap<String, String> params);

    /**
     * 广告点击量维护
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("homemaker/addCount.do")
    Observable<ResultInfo> adCount(@FieldMap() HashMap<String, String> params);

    /**
     * 装修申请提交
     *
     * @param params
     * @return
     */
    @Multipart
    @POST("homemaker/decortApprove.do")
    Observable<ResultInfo> submitDecortApprove(@PartMap() Map<String, RequestBody> params);

    /**
     * 修改/忘记密码
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("member/modPwd.do")
    Observable<ResultInfo> modPwd(@FieldMap() HashMap<String, String> params);

    /**
     * 退出登录
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("member/logout.do")
    Observable<ResultInfo> loginOut(@FieldMap() HashMap<String, String> params);

    /**
     * 生成装修申新请记录
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("homemaker/crtDecora.do")
    Observable<ResultInfo<Approve>> crtDecora(@FieldMap() HashMap<String, String> params);

    /**
     * 居住情况列表
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("member/livingCond.do")
    Observable<ResultListInfo<LiveInfoList>> livingCond(@FieldMap() HashMap<String, String> params);

    /**
     * 家庭成员列表
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("member/myFamily.do")
    Observable<ResultListInfo<MenberInfo>> memberList(@FieldMap() HashMap<String, String> params);

    /**
     * 校验验证码
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("member/chkValidCode.do")
    Observable<ResultInfo> chkValidCode(@FieldMap() HashMap<String, String> params);

    /**
     * 添加成员
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("member/addFamilyMember.do")
    Observable<ResultInfo> addFamilyMember(@FieldMap() HashMap<String, String> params);

    /**
     * 报修获取房产信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST("homemaker/getMemberHoseInfo.do")
    Observable<ResultListInfo<HouseInfo>> loadEstate(@FieldMap() HashMap<String, String> params);

    /**
     * 报修获取房产信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST("community/communityList.do")
    Observable<ResultListInfo<CellInfo>> loadCell(@FieldMap() HashMap<String, String> params);

    /**
     * 获取物业意见
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("homemaker/opinion.do")
    Observable<ResultListInfo<AdviceList>> opinion(@FieldMap() HashMap<String, String> params);

    /**
     * 首页数据
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("homemaker/getIndexData.do")
    Observable<HomeData> getIndexData(@FieldMap() HashMap<String, String> params);

    /**
     * 检查手机号码是否注册
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("member/checkMobile.do")
    Observable<ResultInfo<IsExists>> checkPhone(@FieldMap() HashMap<String, String> params);

    /**
     * 装修进度
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("homemaker/curProgress.do")
    Observable<ResultListInfo<CurProgress>> curProgress(@FieldMap() HashMap<String, String> params);

    /**
     * 装修申请
     *
     * @param params
     * @return
     */
    @Multipart
    @POST("homemaker/decortApprove.do")
    Observable<ResultInfo<Approveid>> decortApprove(@PartMap() Map<String, RequestBody> params);

    /**
     * 待装修房的信息
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("homemaker/houseInfo.do")
    Observable<ResultInfo<FitmentInfo>> houseInfo(@FieldMap() HashMap<String, String> params);

    /**
     * 查看装修人员
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("homemaker/queryPersonInfo.do")
    Observable<ResultListInfo<RegsitForm>> queryPersonInfo(@FieldMap() HashMap<String, String> params);

    /**
     * 添加装修
     *
     * @param params
     * @return
     */
    @Multipart
    @POST("homemaker/savePersonInfo.do")
    Observable<ResultInfo<AddRegsit>> savePersonInfo(@PartMap() Map<String, RequestBody> params);

    /**
     * 删除装修成员
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("homemaker/delPersonInfoById.do")
    Observable<ResultInfo> delPersonInfoById(@FieldMap() HashMap<String, String> params);

    /**
     * 获取装装修规定的h5
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("html/decorateUrl.do")
    Observable<ResultInfo<String>> getFitUrl(@FieldMap() HashMap<String, String> params);

    /**
     * 取消装修申请
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("homemaker/cancelDecortApprove.do")
    Observable<ResultInfo> cancelDecortApprove(@FieldMap() HashMap<String, String> params);

    /**
     * 根据ID获取小区列表
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("community/commuList.do")
    Observable<ResultListInfo<CellInfo>> loadVillageById(@FieldMap() HashMap<String, String> params);

    /**
     * 根据ID获取建筑列表
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("building/buildingList.do")
    Observable<ResultListInfo<Building>> loadBuildingById(@FieldMap() HashMap<String, String> params);

    /**
     * 根据ID获取单元列表
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("unit/list.do")
    Observable<ResultListInfo<Unit>> loadUnitById(@FieldMap() HashMap<String, String> params);

    /**
     * 根据ID获取单元列表
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("house/houseList.do")
    Observable<ResultListInfo<RoomInfo>> loadRoomsById(@FieldMap() HashMap<String, String> params);

    /**
     * 获取省份
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("region/listProvince.do")
    Observable<ResultListInfo<Province>> loadProvince(@FieldMap() HashMap<String, String> params);

    /**
     * 根据ID获取城市
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("region/listCity.do")
    Observable<ResultListInfo<CityInfo>> loadCity(@FieldMap() HashMap<String, String> params);

    /**
     * 获取设备信息
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("repairOrder/getEquiInfo.do")
    Observable<ResultInfo<DiviceInfo>> getDivice(@FieldMap() HashMap<String, String> params);

    /**
     * 确认提交装修申请
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("homemaker/confirmDecortApprove.do")
    Observable<ResultInfo> confirmDecortApprove(@FieldMap() HashMap<String, String> params);

    /**
     * 确认缴费
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("homemaker/confirmPayment.do")
    Observable<ResultListInfo<CorfimPayFees>> confirmPayment(@FieldMap() HashMap<String, String> params);

    /**
     * 支付
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("pay/pay.do")
    Observable<PayInfo> pay(@FieldMap() HashMap<String, String> params);

    /**
     * 家政扫码支付
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("mobile/jiazheng_pay/pay.do")
    Observable<PayInfo> paytoJiazheng(@FieldMap() HashMap<String, String> params);

    /**
     * 注销为游客
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("member/modFamilyMember.do")
    Observable<ResultInfo> modFamilyMember(@FieldMap() HashMap<String, String> params);

    /**
     * 通过城市名字获取小区列表
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("community/commuListByCityName.do")
    Observable<ResultListInfo<CellInfo>> loadCellByName(@FieldMap() HashMap<String, String> params);

    /**
     * 根据城市获取区
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("region/listRegion.do")
    Observable<ResultListInfo<AreaInfo>> loadAreasById(@FieldMap() HashMap<String, String> params);

    /**
     * 装修申请离场
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("homemaker/applyLeave.do")
    Observable<ResultInfo> applyLeave(@FieldMap() HashMap<String, String> params);

    /**
     * 物业意见  （改后）
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("homemaker/opinion.do")
    Observable<ResultListInfo<ListAdvice>> getAdviceList(@FieldMap() HashMap<String, String> params);

    /**
     * 获取开门的钥匙串
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("openDoor/keys.do")
    Observable<ResultListInfo<String>> openKey(@FieldMap() HashMap<String, String> params);

    @FormUrlEncoded
    @POST("openDoor/logging.do")
    Observable<ResultInfo> log(@FieldMap() HashMap<String, String> params);

    /**
     * 获取城市
     *
     * @return
     */
    @FormUrlEncoded
    @POST("region/cityList.do")
    Observable<ResultListInfo<TownInfo>> loadCities(@FieldMap() HashMap<String, String> params);

    /**
     * 获取投诉详情
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("complaint/getComplaintById.do")
    Observable<ResultInfo<ComplaintHistroy>> loadComplaintDetail(@FieldMap() HashMap<String, String> params);

    /**
     * 获取投诉详情
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("repairOrder/getRepairOrderById.do")
    Observable<ResultInfo<RepairHistory>> loadRepairDetail(@FieldMap() HashMap<String, String> params);

    /**
     * 钥匙管理
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("opendoor/key_management.do")
    Observable<ResultInfo<String>> keyManager(@FieldMap() HashMap<String, String> params);


    /**
     * 获取小区信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST("member/authorization.do")
    Observable<ResultInfo<CellState>> loadCellState(@FieldMap() HashMap<String, String> params);

    /**
     * 版本更新
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("commons/getLastversion.do")
    Observable<ResultInfo<VersionInfo>> checkVersion(@FieldMap() HashMap<String, String> params);

    /**
     * 检查补丁操作
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("commons/checkUpdate.do")
    Observable<EntityResult<AnFixBean>> update(@FieldMap() HashMap<String, String> params);

    /**
     * 获取装修订单id
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("homemaker/getCurProgessIds.do")
    Observable<ResultListInfo<String>> getCurProgessIds(@FieldMap() HashMap<String, String> params);

    /**
     * 获取装修进度
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("homemaker/curProgressEn.do")
    Observable<ResultListInfo<FitProcess>> curProgressEn(@FieldMap() HashMap<String, String> params);

    @FormUrlEncoded
    @POST("homemaker/delAttachment.do")
    Observable<ResultInfo> delAttachment(@FieldMap() HashMap<String, String> params);

    /**
     * 我的钱包
     */
    @FormUrlEncoded
    @POST("memberAccount/myAccount.do")
    Observable<ResultInfo<WalletInfo>> loadmywallet(@FieldMap() HashMap<String, String> params);

    /**
     * 余额筛选条件
     */
    @FormUrlEncoded
    @POST("memberAccount/accountScreen.do")
    Observable<ResultListInfo<FilterInfo>> loadFilter(@FieldMap() HashMap<String, String> params);

    /**
     * 收支列表
     */
    @FormUrlEncoded
    @POST("memberAccount/accountList.do")
    Observable<ResultListInfo<BalanceInfo>> loadBalances(@FieldMap() HashMap<String, String> params);

    /**
     * 找回支付密码
     */
    @FormUrlEncoded
    @POST("memberAccount/modifPayPassword.do")
    Observable<ResultInfo> getbackPay(@FieldMap() HashMap<String, String> params);

    /**
     * 修改支付密码
     */
    @FormUrlEncoded
    @POST("memberAccount/modifPayPassword.do")
    Observable<ResultInfo> changePay(@FieldMap() HashMap<String, String> params);

    /**
     * 获取现金券
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("memberCashCoupon/myCashCoupon.do")
    Observable<ResultListInfo<CouponInfo>> loadCoupon(@FieldMap() HashMap<String, String> params);

    /**
     * 获取城市id
     */
    @FormUrlEncoded
    @POST("region/getCityIdByName.do")
    Observable<ResultInfo<CityBean>> loadCityId(@FieldMap() HashMap<String, String> params);

    /**
     * 获取首页配置的按钮
     */
    @FormUrlEncoded
    @POST("commons/appIndexBtn.do")
    Observable<ModuleResult> loadmodule(@FieldMap() HashMap<String, String> params);

    /**
     * 发现好货
     */
    @FormUrlEncoded
    @POST("goods-cat/catList.do")
    Observable<ResultListInfo<FindGoodsId>> FindGoods(@FieldMap() HashMap<String, String> params);

    /**
     * 好货商品
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("goods-cat/getCatChild.do")
    Observable<ResultListInfo<GoodsList>> Goods(@FieldMap() HashMap<String, String> params);

    /**
     * 物业缴费选择
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("homemaker/indexToPayBills.do")
    Observable<ResultListInfo<SelectFees>> payFees(@FieldMap() HashMap<String, String> params);

    /**
     * 检测用户是否存在
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("memberCashCoupon/vaildMember.do")
    Observable<ResultInfo> checkUserExist(@FieldMap() HashMap<String, String> params);

    /**
     * 转让现金券
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("memberCashCoupon/transferred.do")
    Observable<ResultInfo> transfer(@FieldMap() HashMap<String, String> params);

    /**
     * 查看他人的信息
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("memberCashCoupon/getCash4MemberInfo.do ")
    Observable<ResultInfo<Transfer>> loadOtherInfo(@FieldMap() HashMap<String, String> params);

    /**
     * 通过城市名字获取小区列表
     *
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST("community/commuListByCityName.do")
    Observable<ResultListInfo<CellInfo>> loadCellIfCityNull(@FieldMap() HashMap<String, String> params);
}
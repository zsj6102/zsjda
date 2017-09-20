package com.colpencil.tenement.Tools;

/**
 * @author 汪 亮
 * @Description: 与Xmpp有关的工具
 * @Email DramaScript@outlook.com
 * @date 2016/7/15
 */
public class XmppTools {

  /*  //标识
    private String tag = "XmppTools";
    //主机地址
    public static final String HOST = "192.168.0.78";
    //domain
    public static final String DOMAIN = "@Colpencil";
    //端口号
    public static final int PORT = 5222;
    //协定
    public static final String CONFERENCE = "@conference.";

    private static XmppTools instance;
    //连接类
    private XMPPConnection con;
    //多人聊天
    private MultiUserChat muc;

    public MultiUserChat getMuc() {
        return muc;
    }

    public void setMuc(MultiUserChat muc) {
        this.muc = muc;
    }

    {
        try {
            Class.forName("org.jivesoftware.smack.ReconnectionManager");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private XmppTools() {
        configure(ProviderManager.getInstance());
        ConnectionConfiguration connConfig = new ConnectionConfiguration(
                HOST, PORT);
        connConfig.setSASLAuthenticationEnabled(false);//是否打开SASLAuthentication
        connConfig.setReconnectionAllowed(true);//是否允许重链接
//        connConfig.setSecurityMode(ConnectionConfiguration.SecurityMode.enabled); //是否开启安全模式
        connConfig.setSendPresence(false);
        con = new XMPPConnection(connConfig);
        con.DEBUG_ENABLED = true;
        Roster.setDefaultSubscriptionMode(Roster.SubscriptionMode.manual);//设置SubscriptionMode为手动模式
        try {
            con.connect();

            con.addConnectionListener(new ConnectionListener() {

                @Override
                public void reconnectionSuccessful() {
                    ColpencilLogger.i(tag, "重连成功");
                }

                @Override
                public void reconnectionFailed(Exception arg0) {
                    ColpencilLogger.i(tag, "重连失败");
                }

                @Override
                public void reconnectingIn(int arg0) {
                    ColpencilLogger.i(tag, "重连中");
                }

                @Override
                public void connectionClosedOnError(Exception e) {
                    ColpencilLogger.i(tag, "连接出错");
                    if(e.getMessage().contains("conflict")){
                        ColpencilLogger.i(tag, "被挤掉了");
                        disConnectServer();
                    }
                }

                @Override
                public void connectionClosed() {
                    ColpencilLogger.i(tag, "连接关闭");
                }
            });

        } catch (Exception e) {
            ColpencilLogger.e(tag, Log.getStackTraceString(e));
        }
    }

    public XMPPConnection getCon() {
        return con;
    }

    public static XmppTools getInstance(){
        if(null == instance)
            instance = new XmppTools();
        return instance;
    }

    *//**
     * 连接服务器
     * @return
     *//*
    public boolean connServer(){
        if(con.isConnected())
            return true;
        try {
            con.connect();
            return true;
        } catch (Exception e) {
            ColpencilLogger.e(tag, Log.getStackTraceString(e));
        }
        return false;
    }

    *//**
     * 断开连接的服务
     *//*
    public void disConnectServer(){
        if(null!=con && con.isConnected())
            con.disconnect();
    }

    *//**
     * 注册
     * @param name
     * @param pwd
     * @return 0 服务端无响应  1成功  2已存在 3 失败
     *//*
    public int regist(String name , String pwd){
        Registration reg = new Registration();
        reg.setType(IQ.Type.SET);
        reg.setTo(con.getServiceName());
        reg.setUsername(name);
        reg.setPassword(pwd);
        reg.addAttribute("Android", "createUser");
        PacketFilter filter = new AndFilter(new PacketIDFilter(reg.getPacketID()));
        PacketCollector col = con.createPacketCollector(filter);
        con.sendPacket(reg);
        IQ result = (IQ) col.nextResult(SmackConfiguration.getPacketReplyTimeout());
        col.cancel();
        if(null==result){
            ColpencilLogger.e( "no response from server");
            return 0;
        }else if(result.getType() == IQ.Type.RESULT){
            ColpencilLogger.e( result.toString());
            return 1;
        }else if(result.getType() == IQ.Type.ERROR){
            ColpencilLogger.e( result.toString());
            if(result.getError().toString().equalsIgnoreCase("conflict(409)")){
                return 2;
            }else{
                return 3;
            }
        }
        return 3;
    }

    *//**
     * 登录 取离线消息，在设置为在线
     * @param name
     * @param pwd
     * @return
     *//*
    public boolean login(String name,String pwd){
        try {
//			SASLAuthentication.supportSASLMechanism("PLAIN", 0);
            con.login(name, pwd);
            getOffLineMessages();
            setPresence(0);
            setPresence(4);
            return true;
        } catch (Exception e) {
            ColpencilLogger.e(tag, Log.getStackTraceString(e));
        }
        return false;
    }

    *//**
     * 修改密码
     * @param pwd
     * @return
     *//*
    public boolean changePwd(String pwd){
        try {
            con.getAccountManager().changePassword(pwd);
            return true;
        } catch (Exception e) {
            ColpencilLogger.e(tag, Log.getStackTraceString(e));
        }
        return false;
    }

    *//**
     * 删除账号，注销
     * @return
     *//*
    public boolean deleteCount(){
        try {
            con.getAccountManager().deleteAccount();
            return true;
        } catch (Exception e) {
            ColpencilLogger.e(tag, Log.getStackTraceString(e));
        }
        return false;
    }

    *//**
     * 获取所有分组
     * @param roster
     * @return
     *//*
    public List<RosterGroup> getGroups(Roster roster){
        List<RosterGroup> list = new ArrayList<RosterGroup>();
        list.addAll(roster.getGroups());
        return list;
    }

    *//**
     * 添加分组，注意：在添加完一个组后一定要在里面添加一个好友，这样这个组才生效
     * @param roster
     * @param groupName
     * @return
     *//*
    public boolean addGroup(Roster roster,String groupName){
        try{
            roster.createGroup(groupName);
            return true;
        }catch(Exception e){
            ColpencilLogger.e(tag, Log.getStackTraceString(e));
        }
        return false;
    }

    *//**
     * 获取所有成员
     * @param roster
     * @return
     *//*
    public List<RosterEntry> getAllEntrys(Roster roster){
        List<RosterEntry> list = new ArrayList<RosterEntry>();
        list.addAll(roster.getEntries());
        return list;
    }

    *//**
     * 获取某一个分组的成员
     * @param roster
     * @param groupName
     * @return
     *//*
    public List<RosterEntry> getEntrysByGroup(Roster roster,String groupName){
        List<RosterEntry> list = new ArrayList<RosterEntry>();
        RosterGroup group = roster.getGroup(groupName);
        list.addAll(group.getEntries());
        return list;
    }

    *//**
     *  获取用户VCard信息
     * @param user
     * @return
     *//*
    public VCard getVCard(String user){
        VCard vCard = new VCard();
        try {
            vCard.load(con, user);
        } catch (Exception e) {
            ColpencilLogger.e(tag, Log.getStackTraceString(e));
            return null;
        }
        return vCard;
    }

    *//**
     * 发送好友请求
     * @param userName
     *//*
    public void sendAddFriendRequest(String userName , Presence.Type type){
        Presence subscription = new Presence(type);
        subscription.setTo(userName);
        con.sendPacket(subscription);
    }

    *//**
     * 添加好友 无分组
     * @param roster
     * @param userName
     * @param name
     * @return
     *//*
    public static boolean addUser(Roster roster, String userName, String name) {
        try {
            roster.createEntry(userName, name, null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    *//**
     * 添加好友 有分组
     * @param roster
     * @param userName
     * @param name
     * @param groupName
     * @return
     *//*
    public static boolean addUser(Roster roster, String userName, String name,
                                  String groupName) {
        try {
            roster.createEntry(userName, name, new String[] { groupName });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    *//**
     * 删除好友
     * @param roster
     * @param userName
     * @return
     *//*
    public boolean removeUser(Roster roster,String userName){
        try {
            if(!userName.contains(con.getServiceName()))
                userName = userName+con.getServiceName();

            XmppTools.getInstance().sendAddFriendRequest(userName,Presence.Type.unsubscribe);

            RosterEntry entry = roster.getEntry(userName);
            if(null!=entry){
                roster.removeEntry(entry);
                return true;
            }
        } catch (Exception e) {
            ColpencilLogger.e( Log.getStackTraceString(e));
        }
        return false;
    }

    *//**
     * 查找用户
     * @param serverDomain
     * @param userName
     * @return
     *//*
    public List<User> searchUsers(String serverDomain, String userName){
        List<User> list = new ArrayList<User>();
        UserSearchManager userSearchManager = new UserSearchManager(con);
        try {
            Form searchForm = userSearchManager.getSearchForm("search."+serverDomain);
            Form answerForm = searchForm.createAnswerForm();
            answerForm.setAnswer("Username", true);
            answerForm.setAnswer("Name", true);
            answerForm.setAnswer("search", userName);
            ReportedData data = userSearchManager.getSearchResults(answerForm, "search."+serverDomain);
            Iterator<ReportedData.Row> rows = data.getRows();
            while(rows.hasNext()){
                User user = new User();
                ReportedData.Row row = rows.next();
                user.setUserName(row.getValues("Username").next().toString());
                user.setName(row.getValues("Name").next().toString());
                ColpencilLogger.i(tag, user.toString());
                list.add(user);
            }
        } catch (Exception e) {
            ColpencilLogger.e(tag, Log.getStackTraceString(e));
        }
        return list;
    }

    *//**
     * 发送文件
     * @param recvUser
     * @param filePath
     *//*
    public void sendFile(String recvUser,String filePath){
        FileTransferManager fileTransferManager = new FileTransferManager(con);
        try {
            final OutgoingFileTransfer outgoingFileTransfer =  fileTransferManager.createOutgoingFileTransfer(recvUser);
            ColpencilLogger.i("上送文件"+filePath);
            outgoingFileTransfer.sendFile(new File(filePath),"outgoingFileTransfer ^_^");
            TenementApplication.getInstance().execRunnable(new Runnable(){
                @Override
                public void run() {
                    while (!outgoingFileTransfer.isDone()) {
                        ColpencilLogger.i("进度:"+outgoingFileTransfer.getProgress());
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            ColpencilLogger.e(Log.getStackTraceString(e));
                        }
                    }
                    ColpencilLogger.i("上送状态"+outgoingFileTransfer.getStatus());
                    if(outgoingFileTransfer.getStatus().equals(FileTransfer.Status.complete))
                        ColpencilLogger.i("上送完毕");
                    else if(outgoingFileTransfer.getStatus().equals(FileTransfer.Status.error))
                        ColpencilLogger.i("上送出错");
                }});
        } catch (Exception e) {
            ColpencilLogger.i("上送文件异常");
            ColpencilLogger.e( Log.getStackTraceString(e));
        }
    }

    *//**
     * 注册文件接收器
     *//*
    public void registRecvFileListener(){
        FileTransferManager fileTransferManager = new FileTransferManager(con);
        fileTransferManager.addFileTransferListener(new FileTransferListener() {
            public void fileTransferRequest(final FileTransferRequest request) {
                final Event event = new Event();
                final IncomingFileTransfer transfer = request.accept();
                try{
                    ColpencilLogger.i("接受文件："+transfer.getFileName());
                    transfer.recieveFile(new File(Environment.getExternalStorageDirectory()+"/"+request.getFileName()));
                    TenementApplication.getInstance().execRunnable(new Runnable(){
                        @Override
                        public void run() {
                            while (!transfer.isDone()) {
                                ColpencilLogger.i("进度:"+transfer.getProgress());
                                event.setProgress(transfer.getProgress());
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    ColpencilLogger.e( Log.getStackTraceString(e));
                                }
                            }
                            ColpencilLogger.i("接受状态"+transfer.getStatus());
                            if(transfer.getStatus().equals(FileTransfer.Status.complete)){
                                ColpencilLogger.i("接受完毕");
                                event.setFlag(1);
                            }
                            else if(transfer.getStatus().equals(FileTransfer.Status.error)){
                                transfer.cancel();
                                ColpencilLogger.i("接受出错");
                                event.setFlag(2);
                            }
                        }});
                }catch(Exception e){
                    ColpencilLogger.e( Log.getStackTraceString(e));
                    ColpencilLogger.e( "文件接收出错");
                    event.setFlag(3);
                    transfer.cancel();
                }
                RxBus.get().post("registRecvFileListener",event);
            }
        });
    }

    *//**
     * 设置状态
     * @param state 0：在线
     *              1：Q我吧
     *              2：忙碌
     *              3：离开
     *              4：隐身
     *              5：离线
     *//*
    public void setPresence(int state){
        Presence presence;
        switch(state){
            case 0:
                presence = new Presence(Presence.Type.available);
                con.sendPacket(presence);
                ColpencilLogger.e("设置在线");
                break;
            case 1:
                presence = new Presence(Presence.Type.available);
                presence.setMode(Presence.Mode.chat);
                con.sendPacket(presence);
                ColpencilLogger.e("Q我吧");
                ColpencilLogger.e( presence.toXML());
                break;
            case 2:
                presence = new Presence(Presence.Type.available);
                presence.setMode(Presence.Mode.dnd);
                con.sendPacket(presence);
                ColpencilLogger.e( "忙碌");
                ColpencilLogger.e( presence.toXML());
                break;
            case 3:
                presence = new Presence(Presence.Type.available);
                presence.setMode(Presence.Mode.away);
                con.sendPacket(presence);
                ColpencilLogger.e( "离开");
                ColpencilLogger.e( presence.toXML());
                break;
            case 4:
                Roster roster = con.getRoster();
                Collection<RosterEntry> entries = roster.getEntries();
                for(RosterEntry entity:entries){
                    presence = new Presence(Presence.Type.unavailable);
                    presence.setPacketID(Packet.ID_NOT_AVAILABLE);
                    presence.setFrom(con.getUser());
                    presence.setTo(entity.getUser());
                    con.sendPacket(presence);
                    ColpencilLogger.e( presence.toXML());
                }
                ColpencilLogger.e( "告知其他用户-隐身");

                presence = new Presence(Presence.Type.unavailable);
                presence.setPacketID(Packet.ID_NOT_AVAILABLE);
                presence.setFrom(con.getUser());
                presence.setTo(StringUtils.parseBareAddress(con.getUser()));
                con.sendPacket(presence);
                ColpencilLogger.e("告知本用户的其他客户端-隐身");
                ColpencilLogger.e( presence.toXML());
                break;
            case 5:
                presence = new Presence(Presence.Type.unavailable);
                con.sendPacket(presence);
                ColpencilLogger.e( "离线");
                ColpencilLogger.e(presence.toXML());
                break;
            default:
                break;
        }
    }

    *//**
     * 获取离线消息
     * @return
     *//*
    public List<Message> getOffLineMessages(){
        List<Message> msgs = new ArrayList<Message>();
        OfflineMessageManager offLineMessageManager = new OfflineMessageManager(con);
        try {
            Iterator<Message> it = offLineMessageManager.getMessages();
            while(it.hasNext()){
                Message msg = it.next();
                ColpencilLogger.i( msg.toXML());
                msgs.add(msg);
            }
            offLineMessageManager.deleteMessages();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msgs;
    }

    *//**
     * 创建会议室
     * @param roomName
     * @param roomPwd 会议室密码
     *//*
    public boolean createRoom(String roomName , String roomPwd , String subject){
        MultiUserChat multiUserChat  = new MultiUserChat(con,roomName+CONFERENCE+con.getServiceName());
        try {
            multiUserChat.create(roomName);
            Form configForm = multiUserChat.getConfigurationForm();
            Form submitForm = configForm.createAnswerForm();
            for(Iterator<FormField> fields = configForm.getFields(); fields.hasNext();){
                FormField formField = fields.next();
                if(!formField.getType().equals(FormField.TYPE_HIDDEN) && formField.getVariable()!=null){
                    submitForm.setDefaultAnswer(formField.getVariable());
                }
            }

            List<String> owners = new ArrayList<String>();
            owners.add(con.getUser());
            submitForm.setAnswer("muc#roomconfig_roomowners", owners);
            submitForm.setAnswer("muc#roomconfig_roomname", roomName);//房间名字
            submitForm.setAnswer("muc#roomconfig_persistentroom", true);//永久存在，不会再用户都推出时destroy
            submitForm.setAnswer("muc#roomconfig_membersonly", true);//只许成员进入
            submitForm.setAnswer("muc#roomconfig_allowinvites", true);//允许邀请
            submitForm.setAnswer("muc#roomconfig_passwordprotectedroom", true);//凭密码进入
            submitForm.setAnswer("muc#roomconfig_roomsecret", roomPwd);//密码
//			submitForm.setAnswer("muc#roomconfig_enablelogging", true);

            multiUserChat.sendConfigurationForm(submitForm);


        } catch (Exception e) {
            ColpencilLogger.e(tag, "创建聊天室 出错");
            ColpencilLogger.e(tag, Log.getStackTraceString(e));
            return false;
        }
        return true;
    }

    *//**
     * 加入聊天室
     * @param user
     * @param pwd 会议室密码
     * @param roomName
     * @return
     *//*
    public MultiUserChat joinRoom(String user,String pwd,String roomName){
        MultiUserChat muc = new MultiUserChat(con,roomName.contains(CONFERENCE)?roomName:roomName+CONFERENCE+con.getServiceName());
        DiscussionHistory history = new DiscussionHistory();
        history.setMaxStanzas(100);
        history.setSince(new Date(2014,01,01));
//		history.setSeconds(1000);
//		history.setSince(new Date());
        try {
            muc.join(user, pwd, history, SmackConfiguration.getPacketReplyTimeout());

            Message msg = muc.nextMessage(1000);
            if(null!=msg)
                ColpencilLogger.i(tag, msg.toXML());
//
//			Message msg2 = muc.nextMessage();
//			if(null!=msg2)
//				SLog.i(tag, msg2.toXML());
//
//			Message msg3 = muc.nextMessage();
//			if(null!=msg3)
//				SLog.i(tag, msg3.toXML());
//
//			Message msg4 = muc.nextMessage();
//			if(null!=msg4)
//				SLog.i(tag, msg4.toXML());

//			Message msg = null;
//			while(null!=(msg = muc.nextMessage())){
//				SLog.i(tag, msg.toXML());
//			}
        } catch (Exception e) {
            ColpencilLogger.e(tag, " 加入 聊天室 出错");
            ColpencilLogger.e(tag, Log.getStackTraceString(e));
            return null;
        }
        return muc;
    }

    *//**
     * 获取会议室成员名字
     * @param muc
     * @return
     *//*
    public List<String> getMUCMembers(MultiUserChat muc){
        List<String> members = new ArrayList<String>();
        Iterator<String> it = muc.getOccupants();
        while(it.hasNext()){
            String name = StringUtils.parseResource(it.next());
            ColpencilLogger.i("成员名字", name);
            members.add(name);
        }
        return members;
    }

    *//**
     * 获取Hostedrooms
     * @return
     *//*
    public List<MucRoom> getAllHostedRooms(){
        List<MucRoom> rooms = new ArrayList<MucRoom>();
        try {
            Collection<HostedRoom> hrooms = MultiUserChat.getHostedRooms(con, con.getServiceName());
            if(!hrooms.isEmpty()){
                for(HostedRoom r:hrooms){
                    RoomInfo roominfo = MultiUserChat.getRoomInfo(con, r.getJid());
                    ColpencilLogger.i("会议室Info", roominfo.toString());
                    MucRoom mr = new MucRoom();
                    mr.setDescription(roominfo.getDescription());
                    mr.setName(r.getName());
                    mr.setJid(r.getJid());
                    mr.setOccupants(roominfo.getOccupantsCount());
                    mr.setSubject(roominfo.getSubject());
                    rooms.add(mr);
                }
            }
        } catch (Exception e) {
            ColpencilLogger.e(tag, " 获取Hosted Rooms 出错");
            ColpencilLogger.e(tag, Log.getStackTraceString(e));
        }
        return rooms;
    }


    *//**
     * 获取已经加入的room列表
     * @return
     *//*
    public List<BookmarkedConference> getJoinedRooms(){
        List<BookmarkedConference> rooms = new ArrayList<BookmarkedConference>();
        try {
            BookmarkManager bm = BookmarkManager.getBookmarkManager(con);
            Collection<BookmarkedConference> cl = bm.getBookmarkedConferences();
            Iterator<BookmarkedConference> it = cl.iterator();
            while(it.hasNext()){
                BookmarkedConference bc = it.next();
                rooms.add(bc);
                ColpencilLogger.i(tag, bc.toString());
            }
        } catch (Exception e) {
            ColpencilLogger.e(tag, Log.getStackTraceString(e));
        }
        return rooms;
    }

    *//**
     * 创建给予聊天室的私聊
     * @param participant   myroom@conference.jabber.org/johndoe
     * @param listener
     * @return
     *//*
    public Chat createPrivateChat(String participant , MessageListener listener){
        return muc.createPrivateChat(participant, listener);
    }

    *//**
     * 用户是否支持聊天室
     * @param user
     * @return
     *//*
//	public boolean isUserSupportMUC(String user){
//		return MultiUserChat.isServiceEnabled(con, user);
//	}

    *//**
     * 离开聊天室
     *//*
    public void leaveRoom(){
        if(null!=muc)
            muc.leave();
        muc = null;
    }


    *//**
     * config的设置
     * @param pm
     *//*
    public void configure(ProviderManager pm) {

        // 私有数据存储
        pm.addIQProvider("query", "jabber:iq:private",
                new PrivateDataManager.PrivateDataIQProvider());

        // 时间
        try {
            pm.addIQProvider("query", "jabber:iq:time",
                    Class.forName("org.jivesoftware.smackx.packet.Time"));
        } catch (ClassNotFoundException e) {
            Log.w("TestClient",
                    "Can't load class for org.jivesoftware.smackx.packet.Time");
        }

        // 交换花名册
        pm.addExtensionProvider("x", "jabber:x:roster",
                new RosterExchangeProvider());

        // 消息时间
        pm.addExtensionProvider("x", "jabber:x:event",
                new MessageEventProvider());

        // 聊天的状态
        pm.addExtensionProvider("active",
                "http://jabber.org/protocol/chatstates",
                new ChatStateExtension.Provider());
        pm.addExtensionProvider("composing",
                "http://jabber.org/protocol/chatstates",
                new ChatStateExtension.Provider());
        pm.addExtensionProvider("paused",
                "http://jabber.org/protocol/chatstates",
                new ChatStateExtension.Provider());
        pm.addExtensionProvider("inactive",
                "http://jabber.org/protocol/chatstates",
                new ChatStateExtension.Provider());
        pm.addExtensionProvider("gone",
                "http://jabber.org/protocol/chatstates",
                new ChatStateExtension.Provider());

        // XHTML
        pm.addExtensionProvider("html", "http://jabber.org/protocol/xhtml-im",
                new XHTMLExtensionProvider());

        // 群聊邀请
        pm.addExtensionProvider("x", "jabber:x:conference",
                new GroupChatInvitation.Provider());

        // 发现 Service # Items
        pm.addIQProvider("query", "http://jabber.org/protocol/disco#items",
                new DiscoverItemsProvider());

        // 发现 Service # Info
        pm.addIQProvider("query", "http://jabber.org/protocol/disco#info",
                new DiscoverInfoProvider());

        // 数据的形成
        pm.addExtensionProvider("x", "jabber:x:data", new DataFormProvider());

        // MUC User
        pm.addExtensionProvider("x", "http://jabber.org/protocol/muc#user",
                new MUCUserProvider());

        // MUC Admin
        pm.addIQProvider("query", "http://jabber.org/protocol/muc#admin",
                new MUCAdminProvider());

        // MUC Owner
        pm.addIQProvider("query", "http://jabber.org/protocol/muc#owner",
                new MUCOwnerProvider());

        // 延时的交互
        pm.addExtensionProvider("x", "jabber:x:delay",
                new DelayInformationProvider());

        // 版本
        try {
            pm.addIQProvider("query", "jabber:iq:version",
                    Class.forName("org.jivesoftware.smackx.packet.Version"));
        } catch (ClassNotFoundException e) {
            // 在这里不确定发生的情况
        }

        // VCard
        pm.addIQProvider("vCard", "vcard-temp", new VCardProvider());

        // 离线消息的请求
        pm.addIQProvider("offline", "http://jabber.org/protocol/offline",
                new OfflineMessageRequest.Provider());

        // 离线消息的指示
        pm.addExtensionProvider("offline",
                "http://jabber.org/protocol/offline",
                new OfflineMessageInfo.Provider());

        // 最后的Activity
        pm.addIQProvider("query", "jabber:iq:last", new LastActivity.Provider());

        // 搜索用户
        pm.addIQProvider("query", "jabber:iq:search", new UserSearch.Provider());

        // 分享群信息
        pm.addIQProvider("sharedgroup",
                "http://www.jivesoftware.org/protocol/sharedgroup",
                new SharedGroupsInfo.Provider());

        // JEP-33: Extended Stanza Addressing
        pm.addExtensionProvider("addresses",
                "http://jabber.org/protocol/address",
                new MultipleAddressesProvider());

        // FileTransfer 文件转移
        pm.addIQProvider("si", "http://jabber.org/protocol/si",
                new StreamInitiationProvider());
        pm.addIQProvider("query", "http://jabber.org/protocol/bytestreams",
                new BytestreamsProvider());
        pm.addIQProvider("open", "http://jabber.org/protocol/ibb",
                new OpenIQProvider());
        pm.addIQProvider("close", "http://jabber.org/protocol/ibb",
                new CloseIQProvider());
        pm.addExtensionProvider("data", "http://jabber.org/protocol/ibb",
                new DataPacketProvider());

        // 隐私
        pm.addIQProvider("query", "jabber:iq:privacy", new PrivacyProvider());
        pm.addIQProvider("command", "http://jabber.org/protocol/commands",
                new AdHocCommandDataProvider());
        pm.addExtensionProvider("malformed-action",
                "http://jabber.org/protocol/commands",
                new AdHocCommandDataProvider.MalformedActionError());
        pm.addExtensionProvider("bad-locale",
                "http://jabber.org/protocol/commands",
                new AdHocCommandDataProvider.BadLocaleError());
        pm.addExtensionProvider("bad-payload",
                "http://jabber.org/protocol/commands",
                new AdHocCommandDataProvider.BadPayloadError());
        pm.addExtensionProvider("bad-sessionid",
                "http://jabber.org/protocol/commands",
                new AdHocCommandDataProvider.BadSessionIDError());
        pm.addExtensionProvider("session-expired",
                "http://jabber.org/protocol/commands",
                new AdHocCommandDataProvider.SessionExpiredError());
    }*/

}

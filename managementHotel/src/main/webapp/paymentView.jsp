<%@ page import="DTO.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Utils.TextCustomizeFormat" %>
<%@ page import="Language.LanguageControl" %><%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 3/11/2020
  Time: 8:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ArrayList<SearchTO> listSearchNew = (ArrayList<SearchTO>) request.getAttribute("listSearchNew");
    ArrayList listUploadResouce = (ArrayList) request.getAttribute("listUploadResouce");
    ArrayList listPriceRoom = (ArrayList) request.getAttribute("listPriceRoom");
    ArrayList listPromote = (ArrayList) request.getAttribute("listPromote");
    ArrayList listServiceAll = (ArrayList) request.getAttribute("listServiceAll");
    ArrayList listAllRoom = (ArrayList) request.getAttribute("listAllRoom");
    String checkIn = request.getParameter("checkIn") != null ? (String) request.getParameter("checkIn") : "";
    String checkOut = request.getParameter("checkOut") != null ? (String) request.getParameter("checkOut") : "";
    String numOfPeo = request.getParameter("numOfPeo") != null ? (String) request.getParameter("numOfPeo") : "0";
    // tính cho việc giảm giá cá nhân
    String codeValue = request.getAttribute("codeValue") != null ? (String) request.getAttribute("codeValue") : "0";
    String songay = request.getAttribute("songay") != null ? (String) request.getAttribute("songay") : "0";
    int songayInt = 0;
    try {
        songayInt = Integer.parseInt(songay);
    } catch (NumberFormatException e) {
        songayInt = 1;
    }
    int codeValueInt = 0;
    if (!codeValue.equals("")) {
        try {
            codeValueInt = Integer.parseInt(codeValue);
        } catch (NumberFormatException e) {
            codeValueInt = 0;
        }
    }
    LanguageControl lang = (LanguageControl) session.getAttribute("LanguageControl");
    String language = session.getAttribute("language") != null ? (String) session.getAttribute("language") : LanguageControl.VN_LAN;
%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="css/home-css.css"/>
    <link rel="stylesheet" href="css/payment-view.css">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <jsp:include page="Library.jsp"></jsp:include>
    <style>
        .header-div-main {
            position: relative !important;
        }
    </style>
    <title>Payment</title>
</head>
<body>
<jsp:include page="header-not-slide.jsp"></jsp:include>
<div class="main"
     style="background: url('./img/136290873.jpg') no-repeat;background-size: cover;min-height:700px;background-position: center;">
    <div class="container main-payment">
        <div class="col-md-12">

            <div class="processing-payment col-md-12" style="padding-top: 20px!important;">
                <!--cac buoc trong thanh toan (processing)-->
                <div class="processing-display-flex" style="margin-left: 200px;">
                    <!--label cho tung muc-->
                    <%
                        if (language.equals(LanguageControl.VN_LAN)) {
                    %>
                    <div class="label-item-payment">Chọn phòng</div>
                    <div class="label-item-payment">Chọn lựa bổ sung</div>
                    <div class="label-item-payment">Đặt phòng</div>
                    <%
                    } else {
                    %>
                    <div class="label-item-payment">Select room</div>
                    <div class="label-item-payment">Additional options</div>
                    <div class="label-item-payment">Booking room</div>
                    <%
                        }%>

                </div>
                <div class="processing-display-flex">
                    <div class="circle active">1</div>
                    <div class="straight"></div>
                    <div class="circle">2</div>
                    <div class="straight"></div>
                    <div class="circle">3</div>
                </div>
            </div>
            <div class="col-md-12" style="display: flex">
                <div class="col-md-9" style="padding:25px;">

                    <div>


                        <div class="intro-booking-room">
                            <%
                                if (language.equals(LanguageControl.VN_LAN)) {
                            %>
                            <h2>Chọn buồng đầu tiên của bạn</h2>
                            <b>Quý khách sẽ được đặt phòng ở mức giá tốt nhất</b> do không phải qua đơn vị trung gian:
                            Quý khách đang ghé thăm trang web của khách sạn.
                            <%} else {%>
                            <h2>Choose your first chamber</h2>
                            <b>You will be booked at the best price</b>
                            by not through intermediaries: You are visiting the website of the hotel.
                            <%}%>
                        </div>
                        <%
                            if (listSearchNew != null) {
                                for (int i = 0; i < listSearchNew.size(); i++) {
                                    KindRoomTO kindRoomTO = listSearchNew.get(i).getKindRoomTO();
                                    RegionTO regionTO = listSearchNew.get(i).getRegionTO();

                                    if (listUploadResouce != null) {
                                        ArrayList<UploadResourceTO> listUploadResourceTO = (ArrayList<UploadResourceTO>) listUploadResouce.get(i);
                                        ArrayList<PriceRoomTO> listPriceRoomTO = (ArrayList<PriceRoomTO>) listPriceRoom.get(i);
                                        ArrayList<PromoteTO> listpromoteTO = (ArrayList<PromoteTO>) listPromote.get(i);
                                        ArrayList listServiceTO = (ArrayList) listServiceAll.get(i);
                                        int listNumOfRoom = (Integer) listAllRoom.get(i);
                                        if (listPriceRoomTO != null && listPriceRoomTO.size() > 0) {
                        %>
                        <div class="carousel slide slide-room" data-ride="carousel" id="slide-gardens-<%=i%>">
                            <div class="kind-room">
                                <%
                                    if (language.equals(LanguageControl.VN_LAN)) {
                                %>
                                <h3><%=kindRoomTO.getName_vi()%>
                                </h3>
                                <%} else {%>
                                <h3><%=kindRoomTO.getName_en()%>
                                </h3>
                                <%}%>
                            </div>
                            <!-- Indicators -->
                            <ul class="carousel-indicators">
                                <%
                                    for (int j = 0; j < listUploadResourceTO.size(); j++) {
                                        if (j == 0) {
                                %>
                                <li data-target="#slide-gardens-<%=i%>" data-slide-to="<%=j%>" class="active"></li>
                                <%
                                } else {
                                %>
                                <li data-target="#slide-gardens-<%=i%>" data-slide-to="<%=j%>" class="active"></li>
                                <%
                                        }
                                    }
                                %>


                            </ul>

                            <!-- The slideshow -->
                            <div class="carousel-inner">
                                <%
                                    for (int j = 0; j < listUploadResourceTO.size(); j++) {

                                        if (j == 0) {
                                %>
                                <div class="carousel-item active">
                                    <img src="<%=listUploadResourceTO.get(j).getFile_url().replace(StaticTO.STATIC_PATH,".\\")%>"
                                         class="img-slide-zoom">
                                </div>
                                <%
                                } else {
                                %>
                                <div class="carousel-item ">
                                    <img src="<%=listUploadResourceTO.get(j).getFile_url().replace(StaticTO.STATIC_PATH,".\\")%>"
                                         class="img-slide-zoom">
                                </div>
                                <%
                                        }
                                    }%>

                            </div>

                            <!-- Left and right controls -->
                            <a class="carousel-control-prev" href="#slide-gardens-<%=i%>" data-slide="prev">
                                <span class="carousel-control-prev-icon"></span>
                            </a>
                            <a class="carousel-control-next" href="#slide-gardens-<%=i%>" data-slide="next">
                                <span class="carousel-control-next-icon"></span>
                            </a>
                        </div>
                        <div class="detail-room">
                            <%

                                if (listNumOfRoom > 0) {
                                    if (listPriceRoomTO != null) {
                                        for (int j = 0; j < listPriceRoomTO.size(); j++) {
                                            PromoteTO promoteTO = (PromoteTO) listpromoteTO.get(j);
                                            ArrayList<ServiceTO> listService = (ArrayList<ServiceTO>) listServiceTO.get(j);

                                            PriceRoomTO priceRoomTO = listPriceRoomTO.get(j);
                                            long price = priceRoomTO.getPrice_1_night() * songayInt;
                                            long discount = 0;
                                            if (promoteTO != null) {
                                                discount = Long.parseLong(promoteTO.getPro_value());
                                            }
                                            // giá phòng sau khi dược giảm giá
                                            long priceDiscount = price - ((price * discount) / 100) - ((price * codeValueInt) / 100);


                            %>
                            <!--gadens online-->
                            <div id="vnpha30475-pack-100-Online-Exclusive-Rate-Garden-View-Bungalow"
                                 class="col-xs-12 fb-results-rate fb-light-bg">
                                <div class="fb-results-rate--left">
                                    <div>
                                        <div class="fb-results-rate-title">
				<span class="details">
					<a href="#" data-key="Details">
						<i class="fa fa-caret-down"></i>
                          <%
                              if (language.equals(LanguageControl.VN_LAN)) {
                          %>
						<span class="fb-translate rate-title"
                              style="color:black;"><%=listPriceRoomTO.get(j).getType_price_room_vi()%></span>
                        <%} else {%>
                        <span class="fb-translate rate-title"
                              style="color:black;"><%=listPriceRoomTO.get(j).getType_price_room_en()%></span>
                        <%}%>
					</a>
				</span>
                                        </div>
                                        <div style="padding-left: 12px;">
				<span style="font-size: 13px; color: red;">
                      <%
                          if (language.equals(LanguageControl.VN_LAN)) {
                      %>
					<span class="fb-translate" data-key="Only-x-accommodations-left" data-mode="-1" data-fallback=""
                          data-disablehtmlclean="false" data-nodefaultlanguagefallback="false" data-placeholders="[3]"
                          placeholder="Chỉ còn 3 phòng nghỉ!">Chỉ còn <%=listNumOfRoom%> phòng nghỉ!</span>
                    <%} else {%>
                    <span class="fb-translate" data-key="Only-x-accommodations-left" data-mode="-1" data-fallback=""
                          data-disablehtmlclean="false" data-nodefaultlanguagefallback="false" data-placeholders="[3]"
                          placeholder="Chỉ còn 3 phòng nghỉ!">Only <%=listNumOfRoom%> rooms!</span>
                    <%}%>

				</span>
                                        </div>
                                    </div>
                                    <!--check promote nếu có -->
                                    <%if (promoteTO != null) {%>
                                    <div class="fb-results-rate-labels-container">
			<span class="fb-discount-tag rate-badge-container" title="Chỉ cung cấp tại phòng">
				<span class="fb-discount-tag-price">
					<span class="fb-translate">-<%=promoteTO.getPro_value()%>%</span>
				</span>
			</span>
                                        <span class="fb-translate campaign-text-color campaign-title"
                                              placeholder="Today's offer!">Today's offer!</span>
                                    </div>
                                    <%}%>
                                    <div style="margin-top: auto;display: flex">
                                        <%
                                            for (int k = 0; k < listService.size(); k++) {


                                        %>
                                        <div class="fb-results-ratekeys fb-container" style="display: flex;">
                                            <div class="col-xs-4 fb-dark-gray fb-container">
                                            <span class="col-xs-12 col-sm-2 fb-container">
                                                 <img src="<%=listService.get(k).getFile_img_url().replace(StaticTO.STATIC_PATH,".\\")%>"
                                                      border="0"/>
                                            </span>
                                            </div>
                                            <div class="col-xs-12 col-sm-10 fb-results-ratekey">
                                                <%
                                                    if (language.equals(LanguageControl.VN_LAN)) {
                                                %>
                                                <span class="fb-translate"
                                                      placeholder="Bao gồm bữa sáng"><%=listService.get(k).getService_name_vi()%></span>
                                                <%} else {%>
                                                <span class="fb-translate"
                                                      placeholder="Bao gồm bữa sáng"><%=listService.get(k).getService_name_en()%></span>
                                                <%}%>
                                            </div>
                                        </div>
                                        <%}%>
                                    </div>
                                </div>
                                <div style="margin-top: 10px; margin-left: -15px"></div>

                                <div class="fb-results-rate--right">
                                    <div>
						<span class="fb-dark-gray fb-price-small-text">
                              <%
                                  if (language.equals(LanguageControl.VN_LAN)) {
                              %>
							<span class="fb-translate" placeholder="1 đêm"><%=songayInt%> đêm</span>
                            <%} else {%>
                            <span class="fb-translate" placeholder="1 đêm"><%=songayInt%> night</span>
                            <%}%>
							<span class="fb-price-params-separator">, </span>
                               <%
                                   if (language.equals(LanguageControl.VN_LAN)) {
                               %>
							<span placeholder="2 người"><%=numOfPeo%> người</span>
                            <%} else {%>
                            <span placeholder="2 người"><%=numOfPeo%> person</span>
                            <%}%>
						</span>
                                    </div>
                                    <div>
                                        <span>
                                              <%
                                                  if (language.equals(LanguageControl.VN_LAN)) {
                                              %>
                                            Giá phòng:
                                            <%} else {%>
                                            Price room:
                                            <%}%>
                                        </span>
                                        <span>
                                           <%=TextCustomizeFormat.currency_format(priceRoomTO.getPrice_1_night())%>
                                        </span>
                                    </div>
                                    <div style="padding-right: 5px; padding-bottom: 5px;">
                                        <%if (promoteTO != null) {%>
                                        <div>

							<span class="fb-price barred-price last-barred-price"
                                  data-price="<%=priceRoomTO.getPrice_1_night()%>"
                                  data-symbol="true">
                                <span><%=TextCustomizeFormat.currency_format(priceRoomTO.getPrice_1_night() * songayInt)%></span>
									<span class="fb-price-currency">₫</span>

							</span>
                                        </div>
                                        <%}%>
                                        <div class="new-price campaign-text-color">
							<span class="fb-price" data-price="98.276617732135" data-symbol="true">
                                <!--giá sản phẩm-->
                                <span><%=TextCustomizeFormat.currency_format(priceDiscount)%></span>
									<span class="fb-price-currency">₫</span>

								</span>

                                        </div>
                                    </div>
                                    <div><!--khuyến mãi-->
                                        <%if (codeValueInt > 0) {%>
                                        <span style="color:red;">Bạn có mã khuyến mãi :<%=codeValueInt%> %</span>
                                        <%}%>
                                    </div>
                                    <button class="btn btn-static fb-font-bold btn--price-select"
                                            data-price="<%=TextCustomizeFormat.currency_format(priceDiscount)%>"
                                            data-kindroom="<%=kindRoomTO.getKindroom_id()%>"
                                            data-region="<%=regionTO.getRegion_id()%>"
                                            data-typeprice="<%=listPriceRoomTO.get(j).getPrice_id()%>"
                                            data-numberofpeople="<%=numOfPeo%>" data-checkin="<%=checkIn%>"
                                            data-checkout="<%=checkOut%>"
                                            <%if(listPriceRoomTO.get(j).getPrice_id()==3){%>data-price-type="ONLINE"
                                            <%}else{%>data-price-type="OFFLINE"<%}%> data-codevalue="<%=codeValueInt%>"
                                    >
                                        <%
                                            if (language.equals(LanguageControl.VN_LAN)) {
                                        %>
                                        <span class="fb-translate "
                                              placeholder="Chọn" style="text-transform: uppercase;">Chọn</span>
                                        <%} else {%>
                                        <span class="fb-translate "
                                              placeholder="Chọn" style="text-transform: uppercase;">Select</span>
                                        <%}%>
                                    </button>

                                </div>
                            </div>
                            <%
                                        }
                                    }
                                }
                            %>

                        </div>
                        <%

                                        }
                                    }
                                }
                            }
                        %>
                    </div>
                </div>

                <!--thanh toan-->
                <div class="col-md-3" style="padding: 25px;padding-top: 110px;">
                    <div id="fb-basket" class="fb-basket-empty stick">
                        <div id="fb-basket-header-and-body"
                             class="container fb-container fb-light-bg hidden-xs hidden-sm">
                            <div id="fb-basket-header" class="container fb-container fb-light-bg">
                                <div class="col-xs-4 fb-container">
                                    <%
                                        if (language.equals(LanguageControl.VN_LAN)) {
                                    %>
                                    <span class="fb-translate col-xs-12 fb-basket-header-total">Tổng</span>
                                    <%} else {%>
                                    <span class="fb-translate col-xs-12 fb-basket-header-total">Total</span>
                                    <%}%>
                                    <div class="hidden-xs hidden-sm col-md-12 fb-basket-header-details nowrap"></div>
                                </div>
                                <div class="col-xs-8 fb-container">
                                    <div class="col-xs-12 fb-price-amount">
					<span class="fb-price" data-price="0" data-symbol="true" data-pricepoint="0">
						<span>0&nbsp;
							<span class="fb-price-currency">₫</span>
						</span>
					</span>
                                    </div>
                                </div>
                            </div>
                            <div id="fb-basket-header-price-details" class="col-xs-12"></div>
                            <div id="fb-basket-body" class="container col-xs-12 fb-container fb-light-bg"
                                 style="max-height: 432px; overflow-y: auto;">
                                <div class="col-xs-12 fb-basket-remain fb-gray">

                                    <%
                                        if (language.equals(LanguageControl.VN_LAN)) {
                                    %>

                                    <span class="fb-translate">Chọn phòng 1</span>
                                    <%} else {%>
                                    <span class="fb-translate">Select room 1</span>
                                    <%}%>
                                </div>
                            </div>
                        </div>
                        <div id="fb-basket-checkout" class="hidden-xs hidden-sm">
                            <button class="btn  btn-continue-payment" disabled="disabled">
                                <%
                                    if (language.equals(LanguageControl.VN_LAN)) {
                                %>
                                <span class="fb-translate">TIẾP</span>
                                <%} else {%>
                                <span class="fb-translate">CONTINUE</span>
                                <%}%>
                            </button>
                            <div id="fb-widget-container"
                                 class="col-xs-12 col-sm-12 col-md-12 fb-container fb-light-bg"></div>
                        </div>
                        <div id="fb-basket-sticky" class="container fb-dark-bg hidden-md hidden-lg">
                            <div class="col-xs-6 col-sm-7 hidden-md hidden-lg">
                                <div class="visible-sm col-sm-6">
                                    <span class="fb-translate">Tổng</span>
                                </div>
                                <div class="col-xs-12 col-sm-6 hidden-md hidden-lg fb-price-amount">
				<span class="fb-price" data-price="0" data-symbol="true">
					<span>0&nbsp;
						<span class="fb-price-currency">₫</span>
					</span>
				</span>
                                </div>
                                <div class="visible-sm col-sm-6">
                                    <a href="#" onclick="return false;">Details
                                        <span class="fb-basket-sticky-toggle">
						<i class="fa fa-caret-down"></i>
					</span>
                                    </a>
                                </div>
                                <div class="col-xs-12 col-sm-6 hidden-md hidden-lg fb-price-params"></div>
                            </div>
                            <div class="col-xs-2 visible-xs fb-basket-sticky-toggle">
                                <i class="fa fa-caret-down"></i>
                            </div>
                            <div class="col-xs-4 col-sm-5 hidden-md hidden-lg fb-basket-sticky-checkout">
                                <button class="btn btn-block fb-action fb-action-flat fb-font-bold theme-btn">
                                <span class="fb-translate" data-key="basket-validate" data-mode="-1" data-fallback=""
                                      data-disablehtmlclean="false" data-nodefaultlanguagefallback="false"
                                      data-placeholders="[]">TIẾP</span>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="loading.jsp"></jsp:include>
<jsp:include page="./Footer.jsp"></jsp:include>
<!--loader-->

<div class="ui dimmer" id="dimmer"
     style="display:none;position: fixed;top:0;left:0;right:0px;bottom:0px;width: 100%;height: 100%;background-color: rgba(224, 214, 214, 0.5);">
    <div class="ui massive text loader">
        <h3 style="color: rgba(124, 37, 41,1)">Loading</h3>
    </div>
</div>

<script src="scripts/payment.js"></script>
</body>
</html>

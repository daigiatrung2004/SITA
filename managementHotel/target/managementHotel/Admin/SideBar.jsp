<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 3/5/2020
  Time: 10:55 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="./css/Admin/SideBar.css?ver=1.3">

<div class="ui left vertical inverted labeled icon sidebar menu">
    <div class="item" id="img-logo">
        <img src="./img/viclogo.png" height="100px;text-align:center;">
    </div>
    <a class="item" href="Admin">
        <i class="home icon"></i>

      <h4 style="vertical-align: inherit;">
            Home
        </h4>
    </a>
    <div class="item" id="menu-hotel">

        <i class="fas fa-hotel icon"></i>
    <h4 style="vertical-align: inherit;">
            Manager Hotel
        </h4>

        <div id="div-manager-hotel-admin">
            <ul class="ul-admin">
                <li><a href="InsertHotelManager">Thêm dữ liệu hotel</a></li>
                <li><a href="ListingPromote">Danh sách mã giảm giá</a></li>
                <li><a href="ListingFurniture">Danh sách các thiết bị</a></li>
                <li><a href="RoomQuick">Tạo phòng nhanh </a></li>
                <li><a href="ImageManagerHotel">Danh sách hình ảnh khách sạn</a></li>
                <li><a href="ListingHotelManager">Danh sách dữ liệu hotel</a></li>


            </ul>
        </div>
    </div>
    <div class="item" id="menu-transport">

        <i class="shipping fast icon"></i>
       <h4 style="vertical-align: inherit;">
            Manager transport
        </h4>

        <div id="div-manager-trans-admin">
            <ul class="ul-admin">
                <li><a href="InsertTransport">Thêm dữ liệu vận chuyển</a></li>
                <li><a href="ListingTransport?type=region">Danh sách vận chuyển từng khu vực</a></li>
                <li><a href="ListingTransport">Danh sách thông tin vận chuyển</a></li>
            </ul>
        </div>
    </div>
    <div class="item" id="anchor-hr">

        <i class="user icon"></i>
        <h4 style="vertical-align: inherit;">
            Settings Humans Resource
        </h4>


        <div id="div-hr-amdin">
            <ul class="ul-admin">
                <li><a href="InsertEmployee">Thêm nhân viên</a></li>
                <li><a href="ListingHR">Danh sách quản lí nhân viên</a></li>
                <li><a href="ListingHR?position=true">Danh sách vị trí làm việc</a></li>
                <li><a href="PostAnnouncement">Thông báo nhân viên</a></li>

            </ul>
        </div>
    </div>

</div>

<script src="./scripts/Admin/SideBar.js?ver=1.0"></script>
<script>
    $(document).ready(function () {
        $('.left.sidebar').sidebar('toggle').sidebar('attach events', '#btn-sidebar');
        $('.menu .item').tab();
    });
</script>


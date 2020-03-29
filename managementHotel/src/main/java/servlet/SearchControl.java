package servlet;

import DAO.BookingDA;
import DAO.SearchDA;
import DAO.UploadResourceDA;
import DTO.SearchTO;
import DTO.StaticTO;
import DTO.UploadResourceTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class SearchControl extends WebServlet {

    @Override
    protected void process(HttpServletRequest request, HttpServletResponse response) {

        try {
            // lấy dữ liệu từ get,post của search
            String location = request.getParameter("location") != null ? (String) request.getParameter("location") : "";
            String checkIn = request.getParameter("checkIn") != null ? (String) request.getParameter("checkIn") : "";
            String checkOut = request.getParameter("checkOut") != null ? (String) request.getParameter("checkOut") : "";
            String numOfPeo = request.getParameter("numOfPeo") != null ? (String) request.getParameter("numOfPeo") : "0";
            String promote = request.getParameter("promote") != null ? (String) request.getParameter("promote") : "";
            SearchDA searchDA = new SearchDA();
            int locationInt, numOfPeoInt;
            try {
                locationInt = Integer.parseInt(location);
            } catch (NumberFormatException e) {
                locationInt = 0;
            }
            try {
                numOfPeoInt = Integer.parseInt(numOfPeo);
            } catch (NumberFormatException e) {
                numOfPeoInt = 0;
            }
            // tìm kiếm dữ liệu cho việc search ở trang index customer
            ArrayList<SearchTO> listSearchTO = searchDA.searchBooking(locationInt, numOfPeoInt);

            // lọc tìm kiếm và thêm dữ liệu ảnh(upload resource)  tránh đụng độ check in
            ArrayList<SearchTO> listSearchNew = new ArrayList<SearchTO>();
            UploadResourceDA uploadResourceDA = new UploadResourceDA();
            BookingDA bookingDA = new BookingDA();
            for (SearchTO searchTO : listSearchTO) {
                if (!bookingDA.checkBooking(searchTO.getKindRoomTO().getKindroom_id(), searchTO.getRegionTO().getRegion_id(), checkOut)) {
                    UploadResourceTO uploadResourceTO = uploadResourceDA.retrieveImgGalery(String.valueOf(StaticTO.DB_KIND_ROOM_NAME+"_"+searchTO.getKindRoomTO().getKindroom_id()));
                    if (uploadResourceTO != null) {
                        SearchTO searchTO1 = new SearchTO(uploadResourceTO, searchTO.getKindRoomTO(), searchTO.getRegionTO());
                        listSearchNew.add(searchTO1);
                    }
                }
            }
            request.setAttribute("numOfPeo",numOfPeo);
            request.setAttribute("listSearchNew", listSearchNew);
            forward("/PaymentLoadView", request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
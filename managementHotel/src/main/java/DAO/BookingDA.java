package DAO;

import DTO.BookingTO;
import DTO.CustomerTO;
import DTO.StaticTO;
import Utils.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class BookingDA extends DAOOject {
    // sử dụng cho kiểm tra search có đụng ngày check in của người không
    public boolean checkBooking(int kind_room_id, int region_id, String checkOut) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int results = 0;
        String sql = "SELECT COUNT(*) AS TOTAL  FROM " + StaticTO.DB_BOOKING_NAME + " B INNER JOIN ROOM R ON R.ROOM_ID=B.ROOM_ID WHERE B.KIND_ROOM_ID=?  AND B.CHECK_IN_DATE>=? AND R.REGION_ID=? AND (R.STATUS=? OR R.STATUS=?) ";
        conn = getConnection();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, kind_room_id);
//            String []splitDate=checkOut.split("/");
//            checkOut=splitDate[0]+"-"+splitDate[1]+"-"+splitDate[2];

            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(checkOut.trim());
            checkOut = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date1);
            System.out.println("date" + checkOut);
            pstmt.setString(2, checkOut);
            pstmt.setInt(3, region_id);
            pstmt.setString(4, StaticTO.ACTIVE_STATUS);
            pstmt.setString(5, StaticTO.BOOKED_STATUS);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                results = rs.getInt("TOTAL");
            }
        } catch (SQLException e) {
            System.out.println("++++checkBooking:" + pstmt.toString() + results);
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("++++checkBooking:" + pstmt.toString() + results);
            e.printStackTrace();
        } finally {
            System.out.println("++++checkBooking:" + pstmt.toString() + results);
            DbUtils.closeQuietly(rs);
        }
        return (results > 0);
    }

    public boolean checkBookingByRoom(int kind_room_id, int region_id, String checkOut, int room_id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int results = 0;
        String sql = "SELECT COUNT(*) AS TOTAL  FROM " + StaticTO.DB_BOOKING_NAME + " B WHERE KIND_ROOM_ID=?  AND B.CHECK_IN_DATE<=? AND REGION_ID=? AND ROOM_ID=? AND B.STATUS=? ";
        conn = getConnection();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, kind_room_id);
//            String []splitDate=checkOut.split("/");
//            checkOut=splitDate[0]+"-"+splitDate[1]+"-"+splitDate[2];

            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(checkOut.trim());
            checkOut = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date1);
            System.out.println("date" + checkOut);
            pstmt.setString(2, checkOut);
            pstmt.setInt(3, region_id);
            pstmt.setInt(4, room_id);
            pstmt.setString(5, StaticTO.ACTIVE_STATUS);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                results = rs.getInt("TOTAL");
            }
        } catch (SQLException e) {
            System.out.println("++++checkBooking:" + pstmt.toString());
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("++++checkBooking:" + pstmt.toString());
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(rs);
        }
        return (results > 0);
    }

    public boolean checkBookingByRoomNotKind(int region_id, String checkOut, int room_id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int results = 0;
        String sql = "SELECT COUNT(*) AS TOTAL  FROM " + StaticTO.DB_BOOKING_NAME + " B WHERE   B.CHECK_IN_DATE<=? AND REGION_ID=? AND ROOM_ID=? AND B.STATUS=?";
        conn = getConnection();
        try {
            pstmt = conn.prepareStatement(sql);

//            String []splitDate=checkOut.split("/");
//            checkOut=splitDate[0]+"-"+splitDate[1]+"-"+splitDate[2];

            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(checkOut.trim());
            checkOut = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date1);
            System.out.println("date" + checkOut);
            pstmt.setString(1, checkOut);
            pstmt.setInt(2, region_id);
            pstmt.setInt(3, room_id);
            pstmt.setString(4, StaticTO.ACTIVE_STATUS);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                results = rs.getInt("TOTAL");
            }
        } catch (SQLException e) {
            System.out.println("++++checkBooking:" + pstmt.toString());
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("++++checkBooking:" + pstmt.toString());
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(rs);
        }
        return (results > 0);
    }

    // thêm dữ liệu customer
    public boolean addCustomer(CustomerTO customerTO) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO " + StaticTO.DB_CUSTOMER_NAME + " VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        conn = getConnection();
        int rs = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            int index = 1;
            pstmt.setLong(index++, customerTO.getCustomer_id());
            pstmt.setString(index++, customerTO.getEmail());
            pstmt.setString(index++, customerTO.getSankey());
            pstmt.setString(index++, customerTO.getEncryptpass());
            pstmt.setString(index++, customerTO.getStart_date());
            pstmt.setString(index++, customerTO.getContact_person());
            pstmt.setString(index++, customerTO.getVerify_person());
            pstmt.setString(index++, customerTO.getStatus());
            pstmt.setString(index++, customerTO.getRemark());
            pstmt.setString(index++, customerTO.getFirstname());
            pstmt.setString(index++, customerTO.getLastname());
            rs = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("addCustomer++++" + pstmt.toString());
            e.printStackTrace();
        } finally {
            System.out.println("addCustomer++++" + pstmt.toString());
            DbUtils.closeQuietly(conn, pstmt);
        }
        return (rs > 0);
    }

    // update customer
    public boolean updateCustomer(CustomerTO customerTO) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "UPDATE  " + StaticTO.DB_CUSTOMER_NAME + " SET email=?,sankey=?,encryptpass=?,start_date=?," +
                "contact_person=?,verify_person=?,status=?,remark=?,firstname=?,lastname=? WHERE customer_id=?";
        conn = getConnection();
        int rs = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            int index = 1;

            pstmt.setString(index++, customerTO.getEmail());
            pstmt.setString(index++, customerTO.getSankey());
            pstmt.setString(index++, customerTO.getEncryptpass());
            pstmt.setString(index++, customerTO.getStart_date());
            pstmt.setString(index++, customerTO.getContact_person());
            pstmt.setString(index++, customerTO.getVerify_person());
            pstmt.setString(index++, customerTO.getStatus());
            pstmt.setString(index++, customerTO.getRemark());
            pstmt.setString(index++, customerTO.getFirstname());
            pstmt.setString(index++, customerTO.getLastname());
            pstmt.setLong(index++, customerTO.getCustomer_id());
            rs = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("addCustomer++++" + pstmt.toString());
            e.printStackTrace();
        } finally {
            System.out.println("addCustomer++++" + pstmt.toString());
            DbUtils.closeQuietly(conn, pstmt);
        }
        return (rs > 0);
    }

    // select customer mới nhất
    public CustomerTO retreiveCustomerLatest() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "SELECT * FROM " + StaticTO.DB_CUSTOMER_NAME + " ORDER BY CUSTOMER_ID DESC LIMIT 1";
        conn = getConnection();
        ResultSet rs = null;
        CustomerTO customer = null;
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            Date start_date = null;
            String start_dateStr = "";
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            if (rs.next()) {
                try {
                    if (rs.getTimestamp("start_date") != null) {
                        start_date = new Date(rs.getTimestamp("start_date").getTime());
                        start_dateStr = dateFormat.format(start_date);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                customer = new CustomerTO(rs.getLong("customer_id"),
                        rs.getString("email"),
                        rs.getString("sankey"),
                        rs.getString("encryptpass"),
                        start_dateStr,
                        rs.getString("contact_person"),
                        rs.getString("verify_person"),
                        rs.getString("status"),
                        rs.getString("remark")
                );

            }

        } catch (SQLException e) {
            System.out.println("retreiveCustomerLatest++++" + pstmt.toString());
            e.printStackTrace();
        } finally {
            System.out.println("retreiveCustomerLatest++++" + pstmt.toString());
            DbUtils.closeQuietly(conn, pstmt);
        }
        return customer;
    }

    // retrieve customer by id
    public CustomerTO retrieveCustomer(long customer_id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        conn = getConnection();
        String sql = "SELECT * FROM " + StaticTO.DB_CUSTOMER_NAME + " WHERE CUSTOMER_ID=? LIMIT 1";
        ResultSet rs = null;
        CustomerTO customer = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, customer_id);
            rs = pstmt.executeQuery();
            Date start_date = null;
            String start_dateStr = "";
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            if (rs.next()) {
                if (rs.getDate("start_date") != null) {
                    start_date = new Date(rs.getTimestamp("start_date").getTime());
                    start_dateStr = dateFormat.format(start_date);
                }
                customer = new CustomerTO(rs.getLong("customer_id"),
                        rs.getString("email"),
                        rs.getString("sankey"),
                        rs.getString("encryptpass"),
                        start_dateStr,
                        rs.getString("contact_person"),
                        rs.getString("verify_person"),
                        rs.getString("status"),
                        rs.getString("remark"),
                        rs.getString("firstname"),
                        rs.getString("lastname")
                );
            }
        } catch (SQLException e) {
            System.out.println("retrieveCustomer++++" + pstmt.toString());
            e.printStackTrace();
        } finally {
            System.out.println("retrieveCustomer++++" + pstmt.toString());
            DbUtils.closeQuietly(rs);
        }
        return customer;
    }

    // Thêm dữ liệu product theo từng booking
    public boolean addBookingProduct(BookingTO bookingTO) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO " + StaticTO.DB_BOOKING_PRODUCT_NAME + " VALUES(?,?,?,?,?)";
        conn = getConnection();
        int rs = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            int index = 1;
            pstmt.setInt(index++, bookingTO.getID());
            pstmt.setLong(index++, bookingTO.getBooking_id());
            pstmt.setInt(index++, bookingTO.getAmount());
            pstmt.setLong(index++, bookingTO.getPay());
            pstmt.setString(index++, bookingTO.getStatus());
            rs = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn, pstmt);
        }
        return (rs > 0);

    }




    // delete danh sách về theo booking id
    public boolean deleteBookingProduct(long booking_id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "DELETE FROM  " + StaticTO.DB_BOOKING_PRODUCT_NAME + " WHERE booking_id=?";
        conn = getConnection();
        int rs = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,booking_id);

            rs = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn, pstmt);
        }
        return (rs > 0);

    }
    // check tồn tại by booking id
    public ArrayList<BookingTO> retrievekBookingProductByBookingId(long booking_id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "SELECT * FROM " + StaticTO.DB_BOOKING_PRODUCT_NAME + " WHERE BOOKING_ID=?";
        conn = getConnection();
        ResultSet rs = null;
        ArrayList<BookingTO> listBooking = new ArrayList<BookingTO>();
        int count = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, booking_id);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                BookingTO bookingTO = new BookingTO(
                        rs.getLong("booking_id"),
                        rs.getString("status"),
                        rs.getInt("ID"),
                        rs.getInt("amount_pr"),
                        rs.getLong("pay")
                );
                listBooking.add(bookingTO);
            }

        } catch (SQLException e) {
            System.out.println("rayKfCvQofBskZiZ5pthcY9w4KPtZ7qK7R++++" + pstmt.toString());
            e.printStackTrace();
        } finally {
            System.out.println("rayKfCvQofBskZiZ5pthcY9w4KPtZ7qK7R++++" + pstmt.toString());
            DbUtils.closeQuietly(rs);
        }
        return listBooking;
    }

    // Check tồn tại booking product
    public boolean checkBookingProduct(int ID, long booking_id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "SELECT COUNT(*) AS TOTAL FROM " + StaticTO.DB_BOOKING_PRODUCT_NAME + " WHERE BOOKING_ID=? AND ID=?";
        conn = getConnection();
        ResultSet rs = null;

        int count = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, booking_id);
            pstmt.setInt(2, ID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("TOTAL");

            }

        } catch (SQLException e) {
            System.out.println("retrieveBookingLatest++++" + pstmt.toString());
            e.printStackTrace();
        } finally {
            System.out.println("retrieveBookingLatest++++" + pstmt.toString());
            DbUtils.closeQuietly(rs);
        }
        return (count > 0);
    }

    // thêm dữ liệu booking
    public boolean addBooking(BookingTO bookingTO) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO " + StaticTO.DB_BOOKING_NAME + " VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        conn = getConnection();
        int rs = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            int index = 1;
            pstmt.setLong(index++, bookingTO.getBooking_id());
            pstmt.setLong(index++, bookingTO.getCustomer_id());
            pstmt.setString(index++, bookingTO.getStart_booking_date());
            pstmt.setString(index++, bookingTO.getCheck_in_date());
            pstmt.setString(index++, bookingTO.getCheck_out_date());
            pstmt.setInt(index++, bookingTO.getKind_room_id());
            pstmt.setInt(index++, bookingTO.getRegion_id());
            pstmt.setInt(index++, bookingTO.getRoom_id());
            pstmt.setString(index++, bookingTO.getStatus());
            pstmt.setString(index++, bookingTO.getRemark());
            pstmt.setString(index++, bookingTO.getCode_booking());
            rs = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn, pstmt);
        }
        return (rs > 0);
    }

    //update booking
    public boolean updateBooking(BookingTO bookingTO) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "UPDATE  " + StaticTO.DB_BOOKING_NAME + " SET customer_id=?,start_booking_date=?,check_in_date=?,check_out_date=?," +
                "kind_room_id=?,region_id=?,room_id=?,status=?,remark=?,code_booking=? WHERE booking_id=?";
        conn = getConnection();
        int rs = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            int index = 1;
            pstmt.setLong(index++, bookingTO.getCustomer_id());
            pstmt.setString(index++, bookingTO.getStart_booking_date());
            pstmt.setString(index++, bookingTO.getCheck_in_date());
            pstmt.setString(index++, bookingTO.getCheck_out_date());
            pstmt.setInt(index++, bookingTO.getKind_room_id());
            pstmt.setInt(index++, bookingTO.getRegion_id());
            pstmt.setInt(index++, bookingTO.getRoom_id());
            pstmt.setString(index++, bookingTO.getStatus());
            pstmt.setString(index++, bookingTO.getRemark());
            pstmt.setString(index++, bookingTO.getCode_booking());
            pstmt.setLong(index++, bookingTO.getBooking_id());
            rs = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("++++updateBooking:" + pstmt.toString());
            e.printStackTrace();
        } finally {
            System.out.println("++++updateBooking:" + pstmt.toString());
            DbUtils.closeQuietly(conn, pstmt);
        }
        return (rs > 0);
    }

    public int countBooking() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "SELECT COUNT(*) AS TOTAL FROM " + StaticTO.DB_BOOKING_NAME;
        conn = getConnection();
        ResultSet rs = null;
        int count = 0;

        try {
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                count = rs.getInt("TOTAL");
            }


        } catch (SQLException e) {
            System.out.println("countBooking++++" + pstmt.toString());
            e.printStackTrace();
        } finally {
            System.out.println("countBooking++++" + pstmt.toString());
            DbUtils.closeQuietly(rs);
        }
        return count;
    }

    // get booking mới nhất
    public BookingTO retrieveBookingLatest() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "SELECT * FROM " + StaticTO.DB_BOOKING_NAME + " ORDER BY BOOKING_ID DESC LIMIT 1";
        conn = getConnection();
        ResultSet rs = null;
        BookingTO bookingTO = null;
        try {
            pstmt = conn.prepareStatement(sql);

            rs = pstmt.executeQuery();
            Date start_booking_date = null, check_in_date = null, check_out_date = null;
            String start_booking_dateStr = "", check_in_dateStr = "", check_out_dateStr = "";
            if (rs.next()) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                try {
                    if (rs.getDate("start_booking_date") != null) {
                        start_booking_date = new Date(rs.getTimestamp("start_booking_date").getTime());
                        start_booking_dateStr = dateFormat.format(start_booking_date);
                    }
                    if (rs.getDate("check_in_date") != null) {
                        check_in_date = new Date(rs.getTimestamp("check_in_date").getTime());
                        check_in_dateStr = dateFormat.format(check_in_date);
                    }
                    if (rs.getDate("check_out_date") != null) {
                        check_out_date = new Date(rs.getTimestamp("check_out_date").getTime());
                        check_out_dateStr = dateFormat.format(check_out_date);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                bookingTO = new BookingTO(rs.getLong("booking_id"),
                        rs.getLong("customer_id"),
                        start_booking_dateStr,
                        check_in_dateStr,
                        check_out_dateStr,
                        rs.getInt("kind_room_id"),
                        rs.getInt("region_id"),
                        rs.getInt("room_id"),
                        rs.getString("status"),
                        rs.getString("remark"),
                        rs.getString("code_booking")
                );
            }

        } catch (SQLException e) {
            System.out.println("retrieveBookingLatest++++" + pstmt.toString());
            e.printStackTrace();
        } finally {
            System.out.println("retrieveBookingLatest++++" + pstmt.toString());
            DbUtils.closeQuietly(rs);
        }
        return bookingTO;
    }

    // get all booking by room id
    public BookingTO retrieveBookingByRoomId(int roomId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "SELECT * FROM " + StaticTO.DB_BOOKING_NAME + " WHERE ROOM_ID=? AND STATUS=? LIMIT 1";
        conn = getConnection();
        ResultSet rs = null;
        BookingTO bookingTO = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, roomId);
            pstmt.setString(2, StaticTO.ACTIVE_STATUS);

            rs = pstmt.executeQuery();
            Date start_booking_date = null, check_in_date = null, check_out_date = null;
            String start_booking_dateStr = "", check_in_dateStr = "", check_out_dateStr = "";
            if (rs.next()) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                try {
                    if (rs.getDate("start_booking_date") != null) {
                        start_booking_date = new Date(rs.getTimestamp("start_booking_date").getTime());
                        start_booking_dateStr = dateFormat.format(start_booking_date);
                    }
                    if (rs.getDate("check_in_date") != null) {
                        check_in_date = new Date(rs.getTimestamp("check_in_date").getTime());
                        check_in_dateStr = dateFormat.format(check_in_date);
                    }
                    if (rs.getDate("check_out_date") != null) {
                        check_out_date = new Date(rs.getTimestamp("check_out_date").getTime());
                        check_out_dateStr = dateFormat.format(check_out_date);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                bookingTO = new BookingTO(rs.getLong("booking_id"),
                        rs.getLong("customer_id"),
                        start_booking_dateStr,
                        check_in_dateStr,
                        check_out_dateStr,
                        rs.getInt("kind_room_id"),
                        rs.getInt("region_id"),
                        rs.getInt("room_id"),
                        rs.getString("status"),
                        rs.getString("remark"),
                        rs.getString("code_booking")
                );
            }

        } catch (SQLException e) {
            System.out.println("retrieveBookingLatest++++" + pstmt.toString());
            e.printStackTrace();
        } finally {
            System.out.println("retrieveBookingLatest++++" + pstmt.toString());
            DbUtils.closeQuietly(conn, pstmt);
        }
        return bookingTO;
    }

    // get all booking
    public ArrayList<BookingTO> retrieveAllBooking() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ArrayList<BookingTO> listBooking = new ArrayList<BookingTO>();
        ResultSet rs = null;
        String sql = "SELECT * FROM " + StaticTO.DB_BOOKING_NAME;
        conn = getConnection();
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Date start_booking_date = null, check_in_date = null, check_out_date = null;
                String start_booking_dateStr = "", check_in_dateStr = "", check_out_dateStr = "";
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                try {
                    if (rs.getDate("start_booking_date") != null) {
                        start_booking_date = new Date(rs.getTimestamp("start_booking_date").getTime());
                        start_booking_dateStr = dateFormat.format(start_booking_date);
                    }
                    if (rs.getDate("check_in_date") != null) {
                        check_in_date = new Date(rs.getTimestamp("check_in_date").getTime());
                        check_in_dateStr = dateFormat.format(check_in_date);
                    }
                    if (rs.getDate("check_out_date") != null) {
                        check_out_date = new Date(rs.getTimestamp("check_out_date").getTime());
                        check_out_dateStr = dateFormat.format(check_out_date);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                BookingTO bookingTO = new BookingTO(rs.getLong("booking_id"),
                        rs.getLong("customer_id"),
                        start_booking_dateStr,
                        check_in_dateStr,
                        check_out_dateStr,
                        rs.getInt("kind_room_id"),
                        rs.getInt("region_id"),
                        rs.getInt("room_id"),
                        rs.getString("status"),
                        rs.getString("remark"),
                        rs.getString("code_booking")
                );
                listBooking.add(bookingTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listBooking;
    }
    // dùng cho thông kê tất cả
    public ArrayList<BookingTO> retrieveAllBookingByTime(String start_date,String end_date) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ArrayList<BookingTO> listBooking = new ArrayList<BookingTO>();
        ResultSet rs = null;
        String sql = "SELECT * FROM " + StaticTO.DB_BOOKING_NAME+" WHERE start_booking_date BETWEEN ? AND ?";
        conn = getConnection();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,start_date);
            pstmt.setString(2,end_date);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Date start_booking_date = null, check_in_date = null, check_out_date = null;
                String start_booking_dateStr = "", check_in_dateStr = "", check_out_dateStr = "";
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                try {
                    if (rs.getDate("start_booking_date") != null) {
                        start_booking_date = new Date(rs.getTimestamp("start_booking_date").getTime());
                        start_booking_dateStr = dateFormat.format(start_booking_date);
                    }
                    if (rs.getDate("check_in_date") != null) {
                        check_in_date = new Date(rs.getTimestamp("check_in_date").getTime());
                        check_in_dateStr = dateFormat.format(check_in_date);
                    }
                    if (rs.getDate("check_out_date") != null) {
                        check_out_date = new Date(rs.getTimestamp("check_out_date").getTime());
                        check_out_dateStr = dateFormat.format(check_out_date);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                BookingTO bookingTO = new BookingTO(rs.getLong("booking_id"),
                        rs.getLong("customer_id"),
                        start_booking_dateStr,
                        check_in_dateStr,
                        check_out_dateStr,
                        rs.getInt("kind_room_id"),
                        rs.getInt("region_id"),
                        rs.getInt("room_id"),
                        rs.getString("status"),
                        rs.getString("remark"),
                        rs.getString("code_booking")
                );
                listBooking.add(bookingTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listBooking;
    }
    // get all booking by month
    public ArrayList<BookingTO> retrieveAllBookingByMonth(int startmonth ,int endmonth) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ArrayList<BookingTO> listBooking = new ArrayList<BookingTO>();
        ResultSet rs = null;
        String sql = "SELECT * FROM " + StaticTO.DB_BOOKING_NAME+" WHERE month(start_booking_date) between ? and ? ";
        conn = getConnection();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,startmonth);
            pstmt.setInt(2,endmonth);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Date start_booking_date = null, check_in_date = null, check_out_date = null;
                String start_booking_dateStr = "", check_in_dateStr = "", check_out_dateStr = "";
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                try {
                    if (rs.getDate("start_booking_date") != null) {
                        start_booking_date = new Date(rs.getTimestamp("start_booking_date").getTime());
                        start_booking_dateStr = dateFormat.format(start_booking_date);
                    }
                    if (rs.getDate("check_in_date") != null) {
                        check_in_date = new Date(rs.getTimestamp("check_in_date").getTime());
                        check_in_dateStr = dateFormat.format(check_in_date);
                    }
                    if (rs.getDate("check_out_date") != null) {
                        check_out_date = new Date(rs.getTimestamp("check_out_date").getTime());
                        check_out_dateStr = dateFormat.format(check_out_date);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                BookingTO bookingTO = new BookingTO(rs.getLong("booking_id"),
                        rs.getLong("customer_id"),
                        start_booking_dateStr,
                        check_in_dateStr,
                        check_out_dateStr,
                        rs.getInt("kind_room_id"),
                        rs.getInt("region_id"),
                        rs.getInt("room_id"),
                        rs.getString("status"),
                        rs.getString("remark"),
                        rs.getString("code_booking")
                );
                listBooking.add(bookingTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listBooking;
    }
    // get all booking by year
    public ArrayList<BookingTO> retrieveAllBookingByYear(int year) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ArrayList<BookingTO> listBooking = new ArrayList<BookingTO>();
        ResultSet rs = null;
        String sql = "SELECT * FROM " + StaticTO.DB_BOOKING_NAME+" WHERE year(start_booking_date)=?";
        conn = getConnection();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,year);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Date start_booking_date = null, check_in_date = null, check_out_date = null;
                String start_booking_dateStr = "", check_in_dateStr = "", check_out_dateStr = "";
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                try {
                    if (rs.getDate("start_booking_date") != null) {
                        start_booking_date = new Date(rs.getTimestamp("start_booking_date").getTime());
                        start_booking_dateStr = dateFormat.format(start_booking_date);
                    }
                    if (rs.getDate("check_in_date") != null) {
                        check_in_date = new Date(rs.getTimestamp("check_in_date").getTime());
                        check_in_dateStr = dateFormat.format(check_in_date);
                    }
                    if (rs.getDate("check_out_date") != null) {
                        check_out_date = new Date(rs.getTimestamp("check_out_date").getTime());
                        check_out_dateStr = dateFormat.format(check_out_date);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                BookingTO bookingTO = new BookingTO(rs.getLong("booking_id"),
                        rs.getLong("customer_id"),
                        start_booking_dateStr,
                        check_in_dateStr,
                        check_out_dateStr,
                        rs.getInt("kind_room_id"),
                        rs.getInt("region_id"),
                        rs.getInt("room_id"),
                        rs.getString("status"),
                        rs.getString("remark"),
                        rs.getString("code_booking")
                );
                listBooking.add(bookingTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listBooking;
    }

    // get all booking by region
    public ArrayList<BookingTO> retrieveAllBookingByRegion(int region_id, String keyword) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ArrayList<BookingTO> listBooking = new ArrayList<BookingTO>();
        ResultSet rs = null;
        String sql = "SELECT * FROM " + StaticTO.DB_BOOKING_NAME + " WHERE REGION_ID=? AND CODE_BOOKING LIKE ?";
        conn = getConnection();
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, region_id);
            pstmt.setString(2, "%" + keyword + "%");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Date start_booking_date = null, check_in_date = null, check_out_date = null;
                String start_booking_dateStr = "", check_in_dateStr = "", check_out_dateStr = "";
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                if (rs.getDate("start_booking_date") != null) {
                    start_booking_date = new Date(rs.getTimestamp("start_booking_date").getTime());
                    start_booking_dateStr = dateFormat.format(start_booking_date);
                }
                if (rs.getDate("check_in_date") != null) {
                    check_in_date = new Date(rs.getTimestamp("check_in_date").getTime());
                    check_in_dateStr = dateFormat.format(check_in_date);
                }
                if (rs.getDate("check_out_date") != null) {
                    check_out_date = new Date(rs.getTimestamp("check_out_date").getTime());
                    check_out_dateStr = dateFormat.format(check_out_date);
                }
                BookingTO bookingTO = new BookingTO(rs.getLong("booking_id"),
                        rs.getLong("customer_id"),
                        start_booking_dateStr,
                        check_in_dateStr,
                        check_out_dateStr,
                        rs.getInt("kind_room_id"),
                        rs.getInt("region_id"),
                        rs.getInt("room_id"),
                        rs.getString("status"),
                        rs.getString("remark"),
                        rs.getString("code_booking")
                );
                listBooking.add(bookingTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listBooking;
    }

    // thêm dữ liệu booking vs transport
    public boolean addBookingTrans(BookingTO bookingTO) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO " + StaticTO.DB_BOOKING_TRANS_NAME + " VALUES(?,?,?)";
        conn = getConnection();
        int rs = 0;
        try {
            pstmt = conn.prepareStatement(sql);
            int index = 1;
            pstmt.setLong(index++, bookingTO.getId_trans_booking());
            pstmt.setLong(index++, bookingTO.getTransport_id());
            pstmt.setLong(index++, bookingTO.getBooking_id());
            rs = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeQuietly(conn, pstmt);
        }
        return (rs > 0);
    }
}

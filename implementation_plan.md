# FixMatch Phase 2.1: Customer Flow Perfection & Shared Mock State

## Goal
Hoàn thiện toàn bộ luồng người dùng (Customer Flow) từ đầu đến cuối một cách chuyên sâu. Không tạo thêm màn hình mới, tái sử dụng các components hiện có. Xây dựng một **Shared Mock State** duy nhất để đảm bảo tính nhất quán (cùng 1 Job ID, cùng 1 Worker, cùng Status xuyên suốt tất cả các màn hình). Tạo kiến trúc trừu tượng (Abstractions) sạch sẽ để chuẩn bị cho Phase 3 (Backend API & Realtime).

## 1. Kiến trúc Mock State chia sẻ (Shared State Architecture)
- **Tạo `ServiceLocator`**: Sử dụng Service Locator pattern để cung cấp duy nhất 1 instance của `FakeJobRepository` và `MockTrackingProvider` cho toàn bộ app thay vì khởi tạo mới ở mỗi màn hình.
- **Thống nhất `JobStatus`**: Cập nhật Enum `JobStatus` chuẩn xác:
  `CREATED`, `SEARCHING_WORKER`, `WORKER_FOUND`, `WORKER_ACCEPTED`, `WORKER_ON_THE_WAY`, `WORKER_ARRIVED`, `JOB_IN_PROGRESS`, `JOB_COMPLETED`, `PAYMENT_PENDING`, `COMPLETED`, `REVIEWED`.
- **UI Mapping**: Ánh xạ Enum sang ngôn ngữ tự nhiên tiếng Việt trên giao diện (Đã gửi yêu cầu, Đang tìm thợ, Thợ đang trên đường...).

## 2. Hoàn thiện các Màn hình Customer Flow
Tập trung tinh chỉnh UX thực tế, xử lý Empty/Error states, Navigation chuẩn xác (Back đúng context, không dead end).

### 2.1. Tìm kiếm & Lựa chọn (Home -> FindingWorker -> WorkerFound -> WorkerProfile)
- **HomeScreen**: Giữ đơn giản. Bấm Category truyền tham số sang `FindingWorker`.
- **FindingWorkerScreen**: Nhận context category. Hiển thị UI Filter (mock), Loading state, Empty state (Không tìm thấy thợ). Nút Back bảo toàn dữ liệu.
- **WorkerFoundScreen**: Hiển thị danh sách card thợ (Avatar, Tên, Rating, Khoảng cách, Giá dự kiến). Bấm vào card -> `WorkerProfile`.
- **WorkerProfileScreen**: Trang chốt quyết định. Hiển thị thông tin trust (Kinh nghiệm, Đánh giá, Chuyên môn). Hai action chính: "Nhắn tin" (secondary) và "Đặt thợ ngay" (Sticky bottom primary). 

### 2.2. Tạo & Quản lý Yêu cầu (NewJobRequest -> MyRequests)
- **NewJobRequestScreen**: Flow rõ ràng (Chọn dịch vụ, Mô tả, Địa chỉ, Thời gian). Validate input. Bấm Submit -> Ghi vào `FakeJobRepository` (State: SEARCHING_WORKER) -> Chuyển sang `MyRequests` hoặc hiển thị Detail.
- **MyRequestsScreen**: Tab Active / Completed. 
  - Đang `SEARCHING_WORKER` -> Nút "Xem trạng thái".
  - `WORKER_ON_THE_WAY` -> Nút "Theo dõi (Track)".
  - `JOB_COMPLETED` -> Nút "Thanh toán".
  - Nếu active request bị huỷ, xử lý UI state hợp lý.

### 2.3. Theo dõi công việc (TrackJob)
- **Map-First UI**: Bản đồ chiếm diện tích lớn, thông tin thợ nằm trong BottomSheet có thể vuốt.
- **Abstract TrackingProvider**: Cung cấp `MockTrackingProvider` (phát toạ độ route).
- **Responsive States**: 
  - `ON_THE_WAY`: Marker di chuyển, ETA giảm dần. CTA: Gọi điện, Nhắn tin.
  - `ARRIVED`: UI chuyển sang "Thợ đã đến".
  - `IN_PROGRESS`: Focus vào trạng thái công việc thay vì map.
- Ngăn chặn map che khuất System Navigation hoặc Bottom Sheet.

### 2.4. Thanh toán & Đánh giá (Payment -> Review)
- Kích hoạt khi Job chuyển sang `JOB_COMPLETED`.
- **PaymentScreen**: Thấy breakdown giá, Mock payment success/failed. Thành công -> Job = `COMPLETED` -> Sang Review. Không back về Home mất context.
- **ReviewScreen**: Đánh giá 1-5 sao. Submit -> Job = `REVIEWED` -> Về Home/MyRequests.

### 2.5. Giao tiếp & Gọi điện (Messages)
- **Messages / Chat**: Context aware (nhắn với đúng ID thợ từ Job). Mock state unread/sent.
- **Mock Call**: Nút gọi mở một dialog mock (Chưa gọi thật).

## 3. Quản lý Edge Cases & Mock Scenarios
Tích hợp một Menu Debug (chỉ dùng cho Dev/Mock) để user/tester có thể tuỳ ý switch qua lại giữa 14 Scenarios:
1. Chưa có request
2. Đang tìm thợ (`SEARCHING_WORKER`)
3. Đã có worker, đang trên đường (`WORKER_ON_THE_WAY`)
4. Thợ đã đến (`WORKER_ARRIVED`)
5. Đang sửa chữa (`JOB_IN_PROGRESS`)
6. Sửa xong, chờ thanh toán (`PAYMENT_PENDING`)
7. Thanh toán thành công (`COMPLETED`)
8. Completed nhưng chưa review
9. Reviewed
10. Không tìm thấy thợ
11. Thợ huỷ
12. Khách huỷ
13. Payment fail
14. Network loading timeout

## Verification Plan
- Chạy thử toàn bộ luồng từ lúc chốt thợ đến lúc thanh toán và đánh giá mà không gặp lỗi logic state.
- Kiểm tra nút Back từ TrackJob về MyRequests không làm hỏng active job.
- Chuyển Scenario từ Menu Debug và xem UI tự động cập nhật realtime.

---

> [!IMPORTANT]
> Vui lòng duyệt kế hoạch này. Khi được xác nhận, tôi sẽ bắt đầu triển khai ngay với việc thiết lập `ServiceLocator` và chuẩn hoá `FakeJobRepository`.

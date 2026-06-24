# FreeJ2ME-Plus (Web & Backend Integration Fork)

Đây là bản fork của [TASEmulators/freej2me-plus](https://github.com/TASEmulators/freej2me-plus) được tùy biến để phục vụ việc nhúng máy ảo Java ME vào các hệ thống backend, máy chủ web streaming, hoặc bot tự động.

Mục tiêu cốt lõi của phiên bản này là **giảm thiểu RAM hao tổn khi chạy nhiều phiên giả lập đồng thời** và **cung cấp các giao diện (API) dễ lập trình** để truyền tải hình ảnh, âm thanh và sự kiện điều khiển ra bên ngoài.

---

## Mục lục

- [Mục tiêu & Kiến trúc](#mục-tiêu--kiến-trúc)
- [Tại sao chọn Headless Bridge thay vì CheerpJ hay J2ME.js?](#tại-sao-chọn-headless-bridge-thay-vì-cheerpj-hay-j2mejs)
- [Tại sao dùng WebSocket thay vì WebRTC?](#tại-sao-dùng-websocket-thay-vì-webrtc)
- [Khả năng tương thích thực tế](#khả-năng-tương-thích-thực-tế)
- [Cài đặt & Biên dịch](#cài-đặt--biên-dịch)
- [Hướng dẫn lập trình (Ví dụ thực tế)](#hướng-dẫn-lập-trình-ví-dụ-thực-tế)
  - [Ví dụ 1: Khởi chạy game cơ bản bằng FreeJ2MEManager](#ví-dụ-1-khởi-chạy-game-cơ-bản-bằng-freej2memanager)
  - [Ví dụ 2: Vòng lặp nhận hình ảnh và gửi phím cho Web Bridge](#ví-dụ-2-vòng-lặp-nhận-hình-ảnh-và-gửi-phím-cho-web-bridge)
  - [Ví dụ 3: Đăng ký SessionListener để xử lý khi game bị crash](#ví-dụ-3-đăng-ký-sessionlistener-để-xử-lý-khi-game-bị-crash)
  - [Ví dụ 4: Cấu hình tự động khôi phục (Auto-Restart) và đọc Hộp đen](#ví-dụ-4-cấu-hình-tự-động-khôi-phục-auto-restart-và-đọc-hộp-đen)
  - [Ví dụ 5: Lấy dữ liệu âm thanh định kỳ ra tệp hoặc luồng phát](#ví-dụ-5-lấy-dữ-liệu-âm-thanh-định-kỳ-ra-tệp-hoặc-luồng-phát)
  - [Ví dụ 6: Xử lý sự kiện cảm ứng kéo thả và bàn phím đa dụng](#ví-dụ-6-xử-lý-sự-kiện-cảm-ứng-kéo-thả-và-bàn-phím-đa-dụng)
  - [Ví dụ 7: Quản lý giới hạn tải và tự động dọn dẹp Session idle](#ví-dụ-7-quản-lý-giới-hạn-tải-và-tự-động-dọn-dẹp-session-idle)
- [Tham số khởi tạo tham chiếu](#tham-số-khởi-tạo-tham-chiếu)
- [Ghi chú kỹ thuật về log lúc đóng ứng dụng](#ghi-chú-kỹ-thuật-về-log-lúc-đóng-ứng-dụng)
- [Giấy phép](#giấy-phép)

---

## Mục tiêu & Kiến trúc

Ở phiên bản gốc, mỗi khi chạy một game J2ME, hệ điều hành phải khởi tạo một máy ảo JVM độc lập kèm theo giao diện cửa sổ AWT/Swing. Khi triển khai trên máy chủ cho 20 người chơi, hệ thống tốn hơn 4GB RAM chỉ để duy trì các tiến trình Java trống.

Bản fork này áp dụng mô hình **Single JVM + ChildFirst ClassLoader Isolation**:
* Tất cả người chơi cùng chia sẻ một tiến trình Java duy nhất.
* Các biến toàn cục tĩnh của giả lập (`Mobile`, `Display`, `RMS`) được nhân bản riêng cho từng `sessionId`.
* Lượng RAM hao tổn cho mỗi phiên chơi mới giảm xuống chỉ còn tương đương dung lượng thực của Rom game và bộ đệm LCD (~35MB - 50MB/session).

---

## Tại sao chọn Headless Bridge thay vì CheerpJ hay J2ME.js?

Các phương án biên dịch chéo bytecode sang JavaScript hoặc WebAssembly chạy trực tiếp trên trình duyệt client có ưu điểm là không tốn tài nguyên máy chủ. Tuy nhiên, trong thực tế dự án gặp các giới hạn:

1. **Khác biệt về quản lý bộ nhớ & AWT:** Đồ họa của Java ME dựa trên hệ thống AWT thô. Khi chuyển đổi sang Canvas của trình duyệt, các luồng vẽ rất dễ bị khựng, xé hình hoặc rò rỉ bộ nhớ khi chạy các tựa game phức tạp có chu kỳ vẽ dày đặc.
2. **Các tập lệnh đặc thù của nhà sản xuất:** Phần lớn thư viện game J2ME hay đến từ Nhật Bản và Hàn Quốc (NTT DoCoMo DoJa, KDDI, SoftBank MascotCapsule 3D) gắn chặt với kiến trúc JVM máy bàn. Trình duyệt client khó tái tạo trọn vẹn các tập lệnh native này.
3. **Bảo mật mã nguồn Rom:** Khi chạy trên client, tệp `.jar` buộc phải tải xuống trình duyệt của người chơi. Chạy trên máy chủ giúp bảo mật tệp game `.jar`, client chỉ đóng vai trò hiển thị kết quả đầu ra.

---

## Tại sao dùng WebSocket thay vì WebRTC?

Dự án lựa chọn giao thức truyền tải hình ảnh qua WebSocket nhị phân vì các lý do thực tế sau:

1. **Tiết kiệm CPU cho máy chủ:** Độ phân giải tiêu chuẩn của điện thoại J2ME rất nhỏ (thường là `176x208` hoặc `240x320`). Việc truyền trực tiếp các mảng điểm ảnh nén (WebP / Delta RGB) qua WebSocket chỉ tiêu tốn băng thông khoảng 100 - 250 KB/s mỗi người chơi. Nếu dùng WebRTC, máy chủ phải liên tục thực hiện mã hóa video H.264/VP8 bằng vi xử lý cho hàng chục luồng đồng thời, gây quá tải CPU trên các máy chủ nhỏ.
2. **Sự thuận tiện khi triển khai:** WebSocket đi qua các cổng HTTP/HTTPS (`ws://`, `wss://`) tiêu chuẩn, dễ dàng cấu hình lọt qua mọi proxy hay CDN thông dụng (Nginx, Cloudflare) mà không cần thiết lập các máy chủ STUN/TURN phức tạp như WebRTC.

---

## Khả năng tương thích thực tế

Một số ví dụ tiêu biểu về các tựa game đòi hỏi độ chính xác máy ảo cao sẽ chạy ổn định trên kiến trúc Bridge này:

* **`Biohazard / Resident Evil: The Missions 3D` (Engine MascotCapsule V3)**: Đòi hỏi tính toán ma trận dựng hình native chính xác.
* **`Asphalt 4 Elite Racing / Galaxy on Fire 2` (Engine chuẩn M3G JSR-184)**: Cần duy trì nhịp lặp xử lý đa luồng đều đặn để không bị rớt khung hình.
* **`Taito Trance Pinball` (Engine âm thanh miniBAE)**: Đòi hỏi bộ tổng hợp nhạc MIDI phần cứng chuẩn cũ của Sun/Oracle.

---

## Cài đặt & Biên dịch

### Yêu cầu môi trường
- Java JDK 8 (Khuyến nghị dùng chuẩn OpenJDK 8)
- Apache Ant

### Biên dịch nhanh
```bash
git clone https://github.com/AbyssalAscendantAsperger/freej2me-plus.git
cd freej2me-plus
ant
```

Sau khi chạy lệnh `ant`, hệ thống sinh ra 2 tệp tại thư mục `build/`:
- **`freej2me.jar`**: Bản dành cho chạy giao diện cửa sổ AWT trên máy cá nhân.
- **`freej2me-lr.jar`**: **Nhân lõi Libretro Headless chuyên dụng cho tích hợp Web Server**.

---

## Hướng dẫn lập trình (Ví dụ thực tế)

Dưới đây là các mẫu code chuẩn xác để sử dụng trực tiếp các API trong nhân Core (`freej2me-lr.jar`).

### Ví dụ 1: Khởi chạy game cơ bản bằng FreeJ2MEManager

Cách đơn giản nhất để tạo một phiên giả lập độc lập từ tệp `.jar` và kiểm tra hoạt động:

```java
import org.recompile.freej2me.manager.FreeJ2MEManager;
import org.recompile.freej2me.session.*;

public class Example1_SimpleBoot {
    public static void main(String[] args) throws Exception {
        FreeJ2MEManager manager = new FreeJ2MEManager();

        // Khởi tạo game với ID "player-01", màn hình 240x320
        String sessId = manager.createSessionForJar(
            "player-01", 
            "./games/mobiarmy.jar", 
            240, 320, 
            "./data_storage/player-01"
        );

        if (sessId != null) {
            System.out.println("Tạo session thành công: " + sessId);
            LibretroEmbeddedSession session = manager.getSession(sessId);
            
            // Kiểm tra trạng thái máy ảo
            if (session.isRunning()) {
                System.out.println("Game đang chạy mượt mà trong bộ nhớ!");
            }
        }
    }
}
```

### Ví dụ 2: Vòng lặp nhận hình ảnh và gửi phím cho Web Bridge

Mô hình tham chiếu khi xây dựng một luồng streaming qua WebSocket cho trình duyệt:

```java
import org.recompile.freej2me.manager.FreeJ2MEManager;
import org.recompile.freej2me.session.*;

public class Example2_WebBridgeLoop {
    public void startBridge(FreeJ2MEManager manager, String sessId) {
        LibretroEmbeddedSession session = manager.getSession(sessId);
        QueueFrameSink frameQueue = session.getFrameSink();

        // Luồng gửi hình ảnh xuống trình duyệt Client
        Thread senderThread = new Thread(() -> {
            while (session.isRunning()) {
                FramePacket pkt = frameQueue.poll();
                if (pkt != null) {
                    byte[] rgbPixels = pkt.getRgbData();
                    // Gọi hàm gửi mảng byte này qua tín hiệu WebSocket cho khách
                    myWebSocketClient.sendBinary(rgbPixels);
                } else {
                    try { Thread.sleep(4L); } catch (Exception e) {}
                }
            }
        });
        senderThread.start();
    }

    // Khi nhận được lệnh bấm phím từ trình duyệt gửi lên
    public void onClientMessageReceived(FreeJ2MEManager manager, String sessId, int j2meKeyCode, boolean isDown) {
        // Gửi trực tiếp vào máy ảo một cách an toàn đa luồng
        manager.sendKey(sessId, j2meKeyCode, isDown);
    }
}
```

### Ví dụ 3: Đăng ký SessionListener để xử lý khi game bị crash

Lắng nghe các sự kiện vòng đời để kịp thời phản hồi khi game gặp ngoại lệ nghiêm trọng:

```java
import org.recompile.freej2me.session.*;

public void registerErrorRecovery(LibretroEmbeddedSession session) {
    session.setSessionListener(new SessionListener() {
        @Override
        public void onSessionStarted(String sessionId) {
            System.out.println("Khởi chạy phiên: " + sessionId);
        }

        @Override
        public void onSessionStopped(String sessionId) {
            System.out.println("Đã giải phóng bộ nhớ phiên: " + sessionId);
        }

        @Override
        public void onSessionCrashed(String sessionId, Throwable error) {
            System.err.println("Phát hiện lỗi văng game ở session: " + sessionId);
            
            // Đọc nguyên nhân chi tiết
            String reason = session.getCrashReason();
            System.err.println("Chi tiết ngoại lệ: " + reason);

            // Thông báo cho người chơi biết game gặp sự cố
            notifyUserBrowserCrash(sessionId, reason);
        }
    });
}
```

### Ví dụ 4: Cấu hình tự động khôi phục (Auto-Restart) và đọc Hộp đen

Khi game gặp lệnh lạ bị sập, tự động khởi động lại và trích xuất nhật ký thao tác trước đó:

```java
import java.util.List;

public void setupCrashReplay(LibretroEmbeddedSession session) {
    // Cấu hình: Nếu crash, cho phép tự động boot lại tối đa 3 lần
    session.setAutoRestartOnCrash(true, 3);

    session.setSessionListener(new SessionListener() {
        @Override
        public void onSessionStarted(String id) {}
        @Override
        public void onSessionStopped(String id) {}

        @Override
        public void onSessionCrashed(String sessionId, Throwable err) {
            // Lấy danh sách tối đa 32 lệnh thao tác gần nhất trước giây phút crash
            List<byte[]> flightRecorderLogs = session.getFlightRecorderHistory();
            
            System.out.println("Ghi nhận được " + flightRecorderLogs.size() + " gói lệnh lịch sử.");
            for (byte[] wirePacket : flightRecorderLogs) {
                // Lưu mảng lệnh này ra file để các lập trình viên gỡ lỗi sau này
                saveDumpToFile("crash_replay_" + sessionId + ".bin", wirePacket);
            }
        }
    });
}
```

### Ví dụ 5: Lấy dữ liệu âm thanh định kỳ ra tệp hoặc luồng phát

Kết nối vào `QueueAudioSink` để trích xuất mảng PCM âm thanh nguyên thủy của game:

```java
import org.recompile.freej2me.session.*;

public void streamGameAudio(LibretroEmbeddedSession session) {
    QueueAudioSink audioQueue = session.getAudioSink();

    Thread audioThread = new Thread(() -> {
        while (session.isRunning()) {
            AudioPacket audioPkt = audioQueue.poll();
            if (audioPkt != null) {
                short[] pcmSamples = audioPkt.getPcmData();
                int sampleRate = audioPkt.getSampleRate();
                int channels = audioPkt.getChannels();

                // Đẩy dữ liệu PCM này vào luồng mã hóa Opus hoặc ghi ra tệp WAV
                pushToAudioOutputDevice(pcmSamples, sampleRate, channels);
            } else {
                try { Thread.sleep(10L); } catch (Exception e) {}
            }
        }
    });
    audioThread.start();
}
```

### Ví dụ 6: Xử lý sự kiện cảm ứng kéo thả và bàn phím đa dụng

API điều khiển hỗ trợ đầy đủ các thao tác chạm màn hình và phím bấm trên điện thoại:

```java
import org.recompile.freej2me.manager.FreeJ2MEManager;

public void handleControls(FreeJ2MEManager manager, String sessId) {
    // 1. Nhấn phím Chọn trái (Softkey Left chuẩn J2ME = -6)
    manager.sendKey(sessId, -6, true);  // Bấm xuống
    manager.sendKey(sessId, -6, false); // Nhả phím

    // 2. Nhấn phím Số 5 trên bàn phím (KeyCode chuẩn = 53)
    manager.sendKey(sessId, 53, true);
    manager.sendKey(sessId, 53, false);

    // 3. Mô phỏng thao tác vuốt màn hình cảm ứng từ trên xuống dưới
    manager.sendTouch(sessId, 120, 50, 0);  // Chạm tay vào tọa độ (120, 50)
    manager.sendTouch(sessId, 120, 100, 2); // Kéo xuống (120, 100)
    manager.sendTouch(sessId, 120, 150, 2); // Kéo tiếp tới (120, 150)
    manager.sendTouch(sessId, 120, 150, 1); // Nhả tay khỏi màn hình
}
```

### Ví dụ 7: Quản lý giới hạn tải và tự động dọn dẹp Session idle

Khi vận hành máy chủ công cộng, cần kiểm soát số lượng phòng chơi và thu hồi bộ nhớ các phòng bỏ trống:

```java
import org.recompile.freej2me.manager.FreeJ2MEManager;

public class Example7_ServerManagement {
    public static void main(String[] args) {
        FreeJ2MEManager manager = new FreeJ2MEManager();

        // 1. Thiết lập giới hạn tối đa 20 phòng chơi đồng thời
        manager.setMaxConcurrentSessions(20);

        // 2. Quy định: Session nào trôi qua 3 phút (180.000 ms) không có tín hiệu input/poll sẽ bị dọn dẹp
        manager.setSessionTimeoutMs(180_000L);

        // Kiểm tra định kỳ thông số máy chủ
        System.out.println("Ngưỡng tải tối đa cấu hình: " + manager.getMaxConcurrentSessions());
        System.out.println("Thời gian chờ hủy idle: " + manager.getSessionTimeoutMs() + " ms");

        // Khi muốn chủ động quét dọn ngay lập tức các phòng gặp lỗi hoặc quá hạn:
        int cleanedCrash = manager.cleanupCrashedSessions();
        int cleanedTimeout = manager.cleanupTimedOutSessions();

        System.out.println("Đã chủ động dọn dẹp " + (cleanedCrash + cleanedTimeout) + " phòng chơi.");

        // Khi tắt máy chủ tổng
        manager.shutdown();
    }
}
```

---

## Tham số khởi tạo tham chiếu

Khi gọi hàm `manager.createSession(sessId, dataDir, args)` thủ công, mảng `args` chuẩn gồm 32 chuỗi:

| Vị trí mảng | Tên tham số | Ý nghĩa | Giá trị ví dụ |
|:---:|:---|:---|:---|
| `args[0]` | Width | Chiều rộng màn hình game | `"240"` |
| `args[1]` | Height | Chiều cao màn hình game | `"320"` |
| `args[2]` | Rotate | Góc xoay (`0`=0°, `1`=90°, `2`=180°, `3`=270°) | `"0"` |
| `args[3]` | PhoneType | Hãng giả lập (`0`=Standard, `1`=LG, `2`=Motorola, `6`=NokiaKeyboard, `8`=Siemens) | `"0"` |
| `args[4]` | FPS | Tốc độ khung hình mục tiêu | `"30"` hoặc `"60"` |
| `args[5]` | Sound | Âm thanh (`1`=Bật, `0`=Tắt) | `"1"` |
| `args[6..31]` | Options | Các cờ tối ưu hóa chuyên sâu | Để `"0"` mặc định |

---

## Ghi chú kỹ thuật về log lúc đóng ứng dụng

Khi chủ động hủy một phiên giả lập (`destroySession` hoặc `session.stop()`), trên màn hình console sẽ xuất hiện thông báo ngoại lệ:

```text
java.lang.InterruptedException: sleep interrupted
java.io.IOException: Interrupted while waiting for queued input
```

Đây là phản ứng bình thường của máy ảo Java khi ngắt các luồng đang ngủ hoặc đang chờ bàn phím. Thông báo này được giữ lại trên console để lập trình viên dễ theo dõi tiến trình dọn dẹp kết thúc đúng quy trình.

---

## Giấy phép

Dự án tuân theo giấy phép mã nguồn mở **GPLv3**.
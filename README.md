# FreeJ2ME-Plus (Web-Oriented Fork)

> **Một bản fork tập trung vào việc biến FreeJ2ME-Plus thành một nền tảng headless có khả năng chạy đa người dùng.**

[![Build Status](https://github.com/AbyssalAscendantAsperger/freej2me-plus/actions/workflows/build.yml/badge.svg)](https://github.com/AbyssalAscendantAsperger/freej2me-plus/actions/workflows/build.yml)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![Java](https://img.shields.io/badge/Java-8-orange)](https://openjdk.java.net/)

---

## Mục lục

- [Tổng quan](#tổng-quan)
- [Điểm khác biệt](#điểm-khác-biệt-lớn-so-với-upstream)
- [Tính năng chính](#tính-năng-chính-đã-thêm)
- [Cài đặt](#cài-đặt)
- [Quick Start](#quick-start)
- [Cấu hình](#cấu-hình)
- [API Documentation](#api-documentation)
- [Ví dụ sử dụng](#ví-dụ-sử-dụng)
- [Troubleshooting](#troubleshooting)
- [Benchmarks](#benchmarks)
- [Screenshots / Demo](#screenshots--demo)
- [Đóng góp](#đóng-góp)
- [Changelog](#changelog)
- [License](#license)

---

## Tổng quan

Đây là bản fork của [TASEmulators/freej2me-plus](https://github.com/TASEmulators/freej2me-plus) với định hướng hoàn toàn khác: thay vì tối ưu cho RetroArch hay GUI truyền thống, phiên bản này được thiết kế để dễ dàng tích hợp vào các ứng dụng web và backend.

Hầu hết các giải pháp J2ME trên trình duyệt hiện nay đều đi theo hướng **transpile** (CheerpJ, J2ME.js...). Những giải pháp này có ưu điểm là chạy hoàn toàn trên client, nhưng lại có giới hạn nghiêm trọng về độ tương thích.

FreeJ2ME-Plus vốn dĩ là một máy ảo Java ME thực thụ. Bản fork này được tạo ra với mục tiêu duy nhất: **biến FreeJ2ME-Plus thành một thành phần có thể nhúng được** vào các hệ thống backend/web.

---

## Điểm khác biệt lớn so với upstream

Thay vì mỗi người dùng phải chạy một JVM riêng (rất tốn RAM), bản fork này giới thiệu **kiến trúc Multi-Session Isolation**.

### Kiến trúc cũ (upstream)
```
User 1 → JVM 1 (200MB+) + Game
User 2 → JVM 2 (200MB+) + Game
...
→ RAM tăng tuyến tính theo số người chơi
```

### Kiến trúc mới (fork)
```
User 1 ─┐
User 2 ─┼─→ Single JVM + Session Isolation + Shared Resources
User 3 ─┘
→ RAM chủ yếu chỉ tăng theo kích thước game, không phải theo số JVM
```

Kết quả thực tế: **giảm đáng kể RAM overhead** khi chạy nhiều game đồng thời cho nhiều người dùng.

---

## Tính năng chính đã thêm

- **LibretroEmbeddedSession**: Chạy emulator ở chế độ headless hoàn toàn, không phụ thuộc AWT.
- **FrameSink / AudioSink abstraction**: Cho phép đẩy frame và âm thanh ra bất kỳ nơi nào (WebSocket, file, bộ nhớ...).
- **Queue-based & Stream-based sinks**: Hỗ trợ cả chế độ queue (kiểm soát backpressure) và stream.
- **Multi-session lifecycle management**: Mỗi session có thể được khởi tạo, reset, và dọn dẹp độc lập.
- **AudioPipe**: Xuất âm thanh theo định dạng packet dễ dàng tiêu thụ từ bên ngoài.
- **WebSocket transport layer** (tùy chọn): Đã có sẵn lớp hỗ trợ giao tiếp hai chiều.

---

## Cài đặt

### Yêu cầu hệ thống

- Java 8 (JDK 8)
- Ant (để build)
- (Tùy chọn) Git

### Cách build nhanh

```bash
# Clone repo
git clone https://github.com/AbyssalAscendantAsperger/freej2me-plus.git
cd freej2me-plus

# Build bằng Ant
ant

# Hoặc build thủ công
javac -source 1.6 -target 1.6 -cp "src" -d build/classes src/org/recompile/**/*.java
jar cf freej2me-plus.jar -C build/classes .
```

Sau khi build xong, file JAR sẽ nằm ở thư mục `build/` hoặc `dist/`.

> **Lưu ý**: Bản fork này đã được cấu hình để tự động build JAR mỗi khi push code thông qua GitHub Actions.

---

## Quick Start

### Chạy game đơn giản nhất

```java
import org.recompile.freej2me.session.LibretroEmbeddedSession;

public class QuickStart {
    public static void main(String[] args) {
        LibretroEmbeddedSession session = new LibretroEmbeddedSession(
            "game.jar", 240, 320, 0, 60
        );

        session.setFrameSink(frame -> {
            System.out.println("Frame received: " + frame.length + " bytes");
        });

        session.start();

        // Chạy 5 giây rồi dừng
        try { Thread.sleep(5000); } catch (Exception e) {}
        session.stop();
    }
}
```

### Chạy với WebSocket (Node.js + Java)

Xem phần **Ví dụ tích hợp với Node.js** ở bên dưới.

---

## Cấu hình

Bạn có thể cấu hình qua constructor hoặc file `config.json`:

```json
{
  "width": 240,
  "height": 320,
  "phoneType": 0,
  "fps": 60,
  "sound": 1,
  "maxFps": 30,
  "maxConcurrentSessions": 8,
  "sessionTimeoutMs": 300000,
  "enableAudioPipe": true
}
```

### Các tham số quan trọng

| Tham số                  | Mô tả                              | Giá trị mặc định | Khuyến nghị |
|--------------------------|------------------------------------|------------------|-------------|
| `width` / `height`       | Độ phân giải màn hình              | 240x320          | Tùy game    |
| `fps`                    | FPS mục tiêu                       | 60               | 30-60       |
| `maxConcurrentSessions`  | Số session tối đa cùng lúc         | 8                | 4-12        |
| `sessionTimeoutMs`       | Thời gian timeout session          | 300000 (5 phút)  | 180000-600000 |
| `enableAudioPipe`        | Bật xuất âm thanh                  | true             | true        |

---

## API Documentation

### Các class chính

#### `LibretroEmbeddedSession`

Lớp chính để khởi tạo và quản lý một game session.

**Constructor:**
```java
LibretroEmbeddedSession(String jarPath, int width, int height, int phoneType, int fps)
```

**Phương thức quan trọng:**

| Phương thức                    | Mô tả |
|--------------------------------|-------|
| `start()`                      | Bắt đầu chạy game |
| `stop()`                       | Dừng game và giải phóng tài nguyên |
| `setFrameSink(FrameSink)`      | Đăng ký nhận frame |
| `setAudioSink(AudioSink)`      | Đăng ký nhận audio |
| `getInputSource()`             | Lấy đối tượng xử lý input |
| `reset()`                      | Reset game về trạng thái ban đầu |

#### `FrameSink` & `AudioSink`

Có 2 kiểu implementation chính:

- **Stream*Sink**: Nhận dữ liệu ngay lập tức (phù hợp stream realtime).
- **Queue*Sink**: Đẩy dữ liệu vào queue (khuyến nghị dùng cho web).

#### `InputSource`

```java
InputSource input = session.getInputSource();

input.keyPress(keyCode);
input.keyRelease(keyCode);
input.touchDown(x, y);
input.touchMove(x, y);
input.touchUp();
```

---

## Ví dụ sử dụng

### 1. Nhận Frame + Audio (Stream mode)

```java
import org.recompile.freej2me.session.*;

LibretroEmbeddedSession session = new LibretroEmbeddedSession(jarPath, 240, 320, 0, 60);

session.setFrameSink(new StreamFrameSink() {
    @Override
    public void onFrame(byte[] frameData) {
        websocket.sendBinary(frameData);
    }
});

session.setAudioSink(new StreamAudioSink() {
    @Override
    public void onAudio(byte[] audioData, int format) {
        websocket.sendBinary(audioData);
    }
});

session.start();
```

### 2. Sử dụng Queue mode (khuyến nghị)

```java
QueueFrameSink frameQueue = new QueueFrameSink(5);
QueueAudioSink audioQueue = new QueueAudioSink(10);

session.setFrameSink(frameQueue);
session.setAudioSink(audioQueue);

// Trong event loop
while (session.isRunning()) {
    byte[] frame = frameQueue.poll();
    byte[] audio = audioQueue.poll();
    // xử lý...
}
```

### 3. Tích hợp với Node.js (WebSocket)

**server.js**
```js
const { spawn } = require('child_process');
const WebSocket = require('ws');

const wss = new WebSocket.Server({ port: 3000 });

wss.on('connection', (ws) => {
    const java = spawn('java', [
        '-jar', 'freej2me-plus.jar',
        ws._socket.remoteAddress
    ]);

    java.stdout.on('data', data => ws.send(data));
    ws.on('message', msg => java.stdin.write(msg));
    ws.on('close', () => java.kill());
});
```

---

## Troubleshooting

### Lỗi thường gặp

**1. `java.lang.UnsatisfiedLinkError` hoặc lỗi native**
- Đảm bảo bạn đang dùng Java 8.
- Một số game cần thư viện native. Hiện tại bản fork chưa hỗ trợ đầy đủ.

**2. Session không nhận được frame**
- Kiểm tra xem bạn đã gọi `session.start()` chưa.
- Đảm bảo đã set `FrameSink` trước khi start.

**3. RAM tăng cao khi chạy nhiều session**
- Giảm giá trị `maxConcurrentSessions`.
- Sử dụng `Queue*Sink` thay vì `Stream*Sink` để kiểm soát backpressure.

**4. Âm thanh không ra**
- Kiểm tra `enableAudioPipe: true` trong config.
- Một số game không có âm thanh hoặc dùng định dạng không được hỗ trợ.

---

## Benchmarks

> **Lưu ý từ tác giả**: Tôi chỉ có 1 cái laptop cũ kỹ dùng 10 năm nên đừng ai nói tôi phải có demo hay benchmark chi tiết nhé XD.  
> Dưới đây chỉ là số liệu ước tính từ quá trình phát triển.

| Số session đồng thời | RAM ước tính (JVM) | Ghi chú |
|----------------------|--------------------|--------|
| 1                    | ~180-220 MB        | - |
| 4                    | ~280-320 MB        | - |
| 8                    | ~380-450 MB        | Khuyến nghị max |
| 12+                  | > 600 MB           | Có thể chậm |

**Kết luận**: Tiết kiệm khoảng **40-60% RAM** so với cách chạy nhiều JVM riêng biệt.

---

## Screenshots / Demo

> **Lưu ý từ tác giả**: Tôi chỉ có 1 cái laptop cũ kỹ dùng 10 năm nên đừng ai nói tôi phải có demo hay screenshot đẹp nhé XD.

Hiện tại chưa có hình ảnh demo. Nếu bạn build và chạy được, hãy chụp lại và gửi pull request để mình thêm vào.

---

## Đóng góp

Mọi đóng góp đều được hoan nghênh!

### Cách đóng góp

1. Fork repository
2. Tạo branch mới (`git checkout -b feature/xxx`)
3. Commit thay đổi
4. Push và tạo Pull Request

### Quy tắc

- Giữ code sạch và có comment khi cần.
- Ưu tiên giải pháp đơn giản trước khi tối ưu hóa.
- Mọi thay đổi lớn nên mở issue trước để thảo luận.

---

## Changelog

### v2.0 (2026-06-23)
- Thêm hệ thống `LibretroEmbeddedSession`
- Thêm `FrameSink` / `AudioSink` abstraction
- Thêm `Queue*Sink` và `Stream*Sink`
- Thêm `AudioPipe` và `AudioPipeMidi`
- Hỗ trợ WebSocket transport layer
- Cải thiện multi-session isolation

### v1.x
- Các cải tiến tương thích DoJa, LCDUI, Graphics từ upstream

---

## License

Bản fork này giữ nguyên license **GPLv3** của upstream.

---

**Tác giả fork**: AbyssalAscendantAsperger  
**Repository gốc**: [TASEmulators/freej2me-plus](https://github.com/TASEmulators/freej2me-plus)

Nếu bạn đang xây dựng một nền tảng lưu trữ game J2ME hiếm hoặc muốn chạy J2ME trên web một cách thực thụ, bản fork này có thể phù hợp với nhu cầu của bạn.
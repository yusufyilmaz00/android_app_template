# AssociumHub

AssociumHub, modern Android geliştirme prensipleri kullanılarak oluşturulmuş bir sosyal platform projesidir. Bu proje, Clean Architecture, MVVM, Dependency Injection ve Jetpack Compose gibi güncel teknolojileri bir araya getirerek ölçeklenebilir ve bakımı kolay bir uygulama mimarisi sunmayı hedefler.

## ✨ Özellikler
- %100 Kotlin & Jetpack Compose: Tamamen modern ve deklaratif UI toolkit'i ile geliştirildi.
- Modern Mimariler: Clean Architecture ve MVVM desenleri kullanılarak katmanlar ayrılmıştır.
- Veritabanı: Room ile lokal veri depolama.
- Bağımlılık Yönetimi (DI): Hilt ile kolay ve standartlaştırılmış bağımlılık yönetimi.
- Asenkron İşlemler: Kotlin Coroutines & Flow ile verimli ve reaktif programlama.

## 🛠️ Kullanılan Teknolojiler
- Kotlin - Ana geliştirme dili.
- Jetpack Compose - Modern ve deklaratif UI.
- Material 3 - Google'ın en yeni tasarım dili.
- Coroutines & Flow - Asenkron programlama.
- Hilt - Bağımlılık yönetimi (Dependency Injection).
- Room - Lokal veritabanı.
- Android Jetpack - Lifecycle, ViewModel, Navigation vb.

## ⚙️ Kurulum ve Çalıştırma
Projeyi yerel makinenizde kurup çalıştırmak için aşağıdaki adımları takip edebilirsiniz.

### Gereksinimler
- Android Studio (En güncel sürüm önerilir, örn: Jellyfish veya sonrası)
- JDK 17 veya üstü

### Adımlar
1. Projeyi Klonlayın: Projeyi GitHub'dan kendi bilgisayarınıza klonlayın.
```
    https://github.com/unluckyprayersorg/agile-based-software-development.git
```
2. Projeyi Android Studio'da Açın:
   - Android Studio'yu başlatın.
   - "Open" seçeneğine tıklayın.
   - Klonladığınız AssociumHub klasörünü seçin ve açın.
    
3. Gradle Senkronizasyonu: Android Studio, projeyi açtıktan sonra gerekli bağımlılıkları indirmek için otomatik olarak bir Gradle senkronizasyonu başlatacaktır. Bu işlem internet hızınıza bağlı olarak birkaç dakika sürebilir.

4. Projeyi Çalıştırın:
- Bir emülatör başlatın veya fiziksel bir Android cihazı bilgisayarınıza bağlayın.
- Android Studio'nun üst menüsündeki Run 'app' butonuna (▶️) tıklayın.

Proje başarıyla derlendikten sonra seçtiğiniz emülatör veya cihaz üzerinde başlayacaktır.

## 🏗️ Mimari (Architecture)
Bu proje, Google tarafından tavsiye edilen modern Android uygulama mimarisini temel alır. Sorumlulukların ayrıştırılması (Separation of Concerns) ilkesine dayanarak, kodun test edilebilirliğini, ölçeklenebilirliğini ve bakımının kolaylığını hedefler.

Mimari, ana olarak üç katmandan oluşur: UI (Arayüz), Domain (İş Mantığı) ve Data (Veri).
```
AssociumHub/
├── app/
│   ├── build.gradle.kts
│   └── src/main/java/com/unluckyprayers/associumhub/
│       ├── di/
│       ├── data/
│       │   ├── local/
│       │   │   ├── dao/
│       │   │   ├── database/
│       │   │   └── model/
│       │   ├── remote/
│       │   │   ├── dto/
│       │   │   └── service/
│       │   └── repository/
│       ├── domain/
│       │   ├── model/
│       │   └── repository/
│       └── ui/
│           ├── navigation/
│           ├── screen/
│           └── components/
└── ...
```

### 1.📲 ui Katmanı (Presentation Layer)
Kullanıcının gördüğü ve etkileşimde bulunduğu her şey bu katmanda yer alır.
- `ui/screen/{screen_name}`: Her ekran kendi paketinde bulunur (home, profile vb.).
  - `HomeScreen.kt' / 'HomeUI.kt`: Jetpack Compose kullanılarak oluşturulmuş "aptal" (stateless) UI bileşenleri ve bunları yöneten "akıllı" (stateful) Composable'lar.
  - `HomeViewModel.kt`: UI'ın ihtiyaç duyduğu durumu (`StateFlow<UiState>`) yönetir ve kullanıcı etkileşimlerini (event) işler.
  - `HomeState.kt`: Bir ekranın o anki tüm durumunu (yükleniyor mu, veri ne, hata var mı vb.) temsil eden bir `data class`.

- `ui/navigation`: Jetpack Navigation Compose kullanılarak ekranlar arası geçiş mantığını ve navigasyon rotalarını (`Routes`) içerir.

- `ui/components`: Birden fazla ekranda kullanılabilen, yeniden kullanılabilir genel Composable bileşenleri (örneğin, `CustomButton`, `LoadingSpinner`) barındırır.

### 2. 🧠 domain Katmanı (Business Layer)
Uygulamanın temel iş kurallarını içerir. Bu katman platformdan (Android'den) tamamen bağımsızdır ve saf Kotlin modülü olarak tasarlanmıştır.

- `domain/model`: Uygulamanın temel nesnelerini (`User`, `Post` vb.) temsil eden temiz ve basit veri sınıfları. Bunlar API veya veritabanı modellerinden farklıdır ve uygulamanın çekirdek mantığını yansıtır.

- `domain/repository`: Veri katmanına nasıl erişileceğini tanımlayan arayüzler (sözleşmeler). Örneğin, `UserRepository` arayüzü, verinin nereden (API, veritabanı?) geldiğini bilmez, sadece veriyi getirme görevini tanımlar.

### 3. 💾 data Katmanı (Data Layer)
Uygulamanın ihtiyaç duyduğu tüm veriyi sağlar ve yönetir. Domain katmanındaki `repository` arayüzlerini uygular (implement eder). "Single Source of Truth" (Tek Gerçeklik Kaynağı) prensibi burada uygulanır.

- `data/repository`: `domain/repository` klasöründeki arayüzlerin somut implementasyonlarını içerir. Verinin ne zaman uzak sunucudan (remote), ne zaman yerel veritabanından (local) alınacağına karar verir ve gelen DTO/Entity modellerini Domain modellerine dönüştürür.
  
- `data/local`: Cihaz üzerinde veri depolama ile ilgili tüm kodları barındırır.
  - `database`: RoomDatabase sınıfının kendisi (`AssociumHubDatabase`).
  - `dao`: Veritabanı sorgularını (CRUD) içeren Room arayüzleri (`SystemMessageDao`).
  - `model (entity)`: Veritabanı tablolarını (@Entity) temsil eden veri sınıfları (`SystemMessageEntity`).

- `data/remote`: Ağ (network) işlemleri ile ilgili kodları barındırır.
  - `service`: Retrofit veya Ktor için API endpoint'lerini tanımlayan arayüzler.
  - `dto (Data Transfer Object)`: API'den gelen JSON yanıtlarını doğrudan karşılayan veri sınıfları.

### 4. 💉 di Katmanı (Dependency Injection)
Bağımlılıkları oluşturmak ve yönetmek için kullanılır. Hilt kütüphanesi sayesinde, bir sınıfın ihtiyaç duyduğu diğer sınıfları manuel olarak oluşturmak yerine, bu işi Hilt'e devrederiz. @Module ve @Provides gibi anotasyonlar ile veritabanı, repository ve servis gibi nesnelerin uygulama yaşam döngüsü boyunca nasıl oluşturulacağı ve sağlanacağı tanımlanır.

## ✍️ Geliştirici
Bu proje, unluckyprayers tarafından geliştirilmektedir:
  - Yusuf Yılmaz       -> https://github.com/yusufyilmaz00
  - Ahmet Emre Yıldız  -> https://github.com/deadbfl
  - Arif Emre Özcengiz -> https://github.com/ArifEmre08

## 🤝 Katkıda Bulunma
Katkılarınız projeyi daha iyi hale getirecektir! Pull request göndermekten veya issue açmaktan çekinmeyin.

## Feature List:

### User Hub (Global User):
  1. [ ] Kayıt ve Giriş
  2. [ ] Topluluk sekmesinden kulüp aratma
  3. [ ] Kulüp sayfalarını görüntümele (Bilgi, Logolar, galeri (resim), etkinler, duyurular)
  4. [ ] Topluluklara üye olma
  5. [ ] Toplulukları follow yapıp duyurularına abone olma ve sürekli bildirim güncellemesi
  6. [ ] Bildirim özelliği
  7. [ ] QR kod okutup etkinliğe katılma

### Circle Hub (Communities):
  1. [ ] Giriş Ekranı
  2. [ ] Profili düzenleme (Topluluk bilgi sayfasında gözükecek)
  3. [ ] Duyuru yapabilme
  4. [ ] Etkinlik oluşturma
  5. [ ] Etkinlikte katılımcı takibi (QR kod ile giriş ve katılım doğrulama)
  6. [ ] Analiz ekranı (Etkinlik bazlı katılımcı sayısı vs.)
  7. [ ] Üniversite ve kurumlara etkinlik başvurusu, belge yollama ve takibi
     
### Core Hub (Regulator/Instutition Panel)
  1. [ ] Etkinlik evrakları ve başvuru açma, takibi
  2. [ ] Kulüp başvurusu alma
  (sonra buraya da çalışılacak) 

# Varlık Yönetim Uygulaması Case Çalışması

## Genel Bakış

Bu proje, kullanıcıların Türk Lirası, Dolar, Altın vb. varlıklara sahip olabileceği ve bu varlıkları yönetebileceği bir varlık yönetim uygulamasıdır. Kullanıcılar sisteme para yatırabilir, para çekebilir, varlıkları arasında exchange işlemi yapabilir ve toplam varlıklarını istediği cinsten görebilir. Kur bilgileri belirli aralıklarla bir web servisten alınacak ve veritabanına kaydedilecektir. Uygulama Java 21 ve Spring & Spring Boot kullanılarak yazılacaktır ve Dockerize edilecektir.

## Teknolojik Gereksinimler

- **Java 21**
- **Spring & Spring Boot**
- **Couchbase Veritabanı**
- **Docker**
- **RestTemplate ve WebClient**
- **Scheduler (Cron Job)**
- **Clean Code, OOP, SOLID Prensipleri**

## İş Akışları ve Gereksinimler

### 1. Kullanıcı Yönetimi

#### 1.1 Kullanıcı Kaydı
- Kullanıcı, e-posta adresi ile sisteme kaydolur.
- Sistem, kullanıcı numarasını otomatik olarak üretir.

#### 1.2 Hesap Açma
- Mevcut kullanıcı, hesap açma işlemi ile kendine hesap oluşturur.
- Hesaplar üç farklı tipte olabilir: DövizHesabı, TlHesabı, AltınHesabı.

### 2. Varlık Yönetimi

#### 2.1 Para Ekleme ve Çıkarma
- Kullanıcı, hesaplarına para ekleyebilir veya çıkarabilir.
- Para ekleme/çıkarma işlemleri için kullanıcı doğrulaması gereklidir.

#### 2.2 Varlık Listeleme
- Kullanıcı, sahip olduğu varlıkları listeleyebilir.
- Varlıklar farklı döviz cinslerinden olabilir ve toplam varlık, kullanıcı tarafından belirlenen döviz cinsinde gösterilebilir.

### 3. Para Transferi

#### 3.1 Hesaplar Arası Transfer
- Kullanıcı, farklı hesaplar arasında para transferi yapabilir.
- Farklı hesaplar arası transferlerde TL bazında 5 TL komisyon alınır.
- Eğer kullanıcının bakiyesi komisyonu ödemek için yeterli değilse, transfer işlemi gerçekleştirilemez ve hata mesajı gösterilir.

### 4. Kur Bilgilerinin Alınması ve Güncellenmesi

#### 4.1 Kur Bilgilerinin Alınması
- Dolar, Euro, Sterlin için: [https://rest.altinkaynak.com/Currency.json](https://rest.altinkaynak.com/Currency.json)
- Altın için: [https://rest.altinkaynak.com/Gold.json](https://rest.altinkaynak.com/Gold.json)
- Kur bilgileri belirli aralıklarla (20 saniyede bir) alınacak ve Couchbase veritabanına kaydedilecektir.

#### 4.2 Scheduler
- Kur bilgilerini almak için bir Cron Job kullanılacaktır.
- Döviz bilgileri için WebClient, altın bilgileri için RestTemplate kullanılacaktır.

## Proje Yapısı

### 1. Kullanıcı Yönetimi
- **Kayıt Ol**: `POST /register`
- **Hesap Aç**: `POST /createAccount`

### 2. Varlık Yönetimi
- **Para Ekleme**: `POST /addFunds`
- **Para Çıkarma**: `POST /withdrawFunds`
- **Varlık Listeleme**: `GET /listAssets`

### 3. Para Transferi
- **Hesaplar Arası Transfer**: `POST /transferFunds`

### 4. Kur Bilgileri
- **Kur Bilgilerini Güncelleme**: `Scheduled Job`
- **Kur Bilgilerini Alma**: `GET /getExchangeRates`

## Veritabanı Yapısı

### Kullanıcı Tablosu
| Alan | Tip | Açıklama |
| ---- | --- | -------- |
| id | String | Kullanıcı ID |
| email | String | Kullanıcı E-posta Adresi |

### Hesap Tablosu
| Alan | Tip | Açıklama |
| ---- | --- | -------- |
| id | String | Hesap ID |
| userId | String | Kullanıcı ID |
| accountType | String | Hesap Türü (Döviz, TL, Altın) |
| balance | Double | Hesap Bakiyesi |

### Kur Bilgileri Tablosu
| Alan | Tip | Açıklama |
| ---- | --- | -------- |
| id | String | Kur Bilgisi ID |
| currency | String | Döviz Cinsi |
| rate | Double | Kur Değeri |
| timestamp | Date | Güncelleme Zamanı |

## Örnek İstekler ve Cevaplar

### Kullanıcı Kaydı

#### Request

```json
POST /register
{
  "email": "example@example.com"
}
```

#### Response

```json

{
  "userId": "abc123",
  "message": "Kullanıcı başarıyla kaydedildi."
}

```


### Hesap Açma

#### Request

```json

POST /createAccount
{
  "userId": "abc123",
  "accountType": "TL"
}

```

#### Response

```json

{
  "accountId": "def456",
  "message": "Hesap başarıyla oluşturuldu."
}


```


### Para Ekleme

#### Request

```json

POST /addFunds
{
  "accountId": "def456",
  "amount": 1000
}


```

#### Response

```json

{
  "balance": 2000,
  "message": "Para başarıyla eklendi."
}


```

### Para Ekleme

#### Request

```json

POST /addFunds
{
  "accountId": "def456",
  "amount": 1000
}


```

#### Response

```json

{
  "balance": 2000,
  "message": "Para başarıyla eklendi."
}


```
## Notlar
- **Tüm işlemler sırasında hata yönetimi yapılmalıdır.**
- **Kullanıcı doğrulaması ve yetkilendirme mekanizmaları sağlanmalıdır.**
- **Veritabanı bağlantıları ve sorguları optimize edilmelidir.**
- **Uygulama Docker ile containerize edilmelidir.**
- **Testler yazılmalı ve CI/CD entegrasyonu sağlanmalıdır.**

#   e n t i t y - m a n a g e m e n t - a p p  
 
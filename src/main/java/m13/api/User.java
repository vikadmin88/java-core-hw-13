package m13.api;

import java.util.Objects;

public class User {
/*
{
      "id": 1,
      "name": "Leanne Graham",
      "username": "Bret",
      "email": "Sincere@april.biz",
      "address": {
            "street": "Kulas Light",
            "suite": "Apt. 556",
            "city": "Gwenborough",
            "zipcode": "92998-3874",
            "geo": {
                  "lat": "-37.3159",
                  "lng": "81.1496"
            }
      },
      "phone": "1-770-736-8031 x56442",
      "website": "hildegard.org",
      "company": {
            "name": "Romaguera-Crona",
            "catchPhrase": "Multi-layered client-server neural-net",
            "bs": "harness real-time e-markets"
      }
}
*/
    private int id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private String website;
    private Address address = new Address();
    private Company company = new Company();

    public User() {
    }

    public User(int id, String name, String username, String email, String phone, String website) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.website = website;
    }

    public User setAddress(User.Address address) {
        this.address = address;
        return this;
    }
    public User setAddressGeo(User.Address.Geo geo) {
        this.address.geo = geo;
        return this;
    }

    public User setCompany(User.Company company) {
        this.company = company;
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    Address getAddress() {
        return address;
    }

    Company getCompany() {
        return company;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", website='" + website + '\'' +
                ", address='" + address.toString() + '\'' +
                ", geo='" + address.geo.toString() + '\'' +
                ", company='" + company.toString() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name) && Objects.equals(username, user.username) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, username, email);
    }


    static class Address {
        private String street;
        private String suite;
        private String city;
        private String zipcode;
        public Geo geo = new Geo();

        public Address() {
        }

        public Address(String street, String suite, String city, String zipcode) {
            this.street = street;
            this.suite = suite;
            this.city = city;
            this.zipcode = zipcode;
        }

        public void setGeo(Geo geo) {
            this.geo = geo;
        }

        public String getStreet() {
            return street;
        }

        public String getSuite() {
            return suite;
        }

        public String getCity() {
            return city;
        }

        public String getZipcode() {
            return zipcode;
        }

        public Geo getGeo() {
            return geo;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public void setSuite(String suite) {
            this.suite = suite;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        @Override
        public String toString() {
            return "{" +
                    "street='" + street + '\'' +
                    ", suite='" + suite + '\'' +
                    ", city='" + city + '\'' +
                    ", zipcode='" + zipcode + '\'' +
                    '}';
        }

        static class Geo {
            private String lat;
            private String lng;

            public Geo() {
            }

            public Geo(String lat, String lng) {
                this.lat = lat;
                this.lng = lng;
            }

            public String getLat() {
                return lat;
            }

            public String getLng() {
                return lng;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            @Override
            public String toString() {
                return "{" +
                        "lat=" + lat +
                        ", lng=" + lng +
                        '}';
            }
        }
    }



    static class Company {
        private String name;
        private String catchPhrase;
        private String bs;

        public Company() {
        }

        public Company(String name, String catchPhrase, String bs) {
            this.name = name;
            this.catchPhrase = catchPhrase;
            this.bs = bs;
        }

        public String getName() {
            return name;
        }

        public String getCatchPhrase() {
            return catchPhrase;
        }

        public String getBs() {
            return bs;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCatchPhrase(String catchPhrase) {
            this.catchPhrase = catchPhrase;
        }

        public void setBs(String bs) {
            this.bs = bs;
        }

        @Override
        public String toString() {
            return "{" +
                    "name='" + name + '\'' +
                    ", catchPhrase='" + catchPhrase + '\'' +
                    ", bs='" + bs + '\'' +
                    '}';
        }
    }
}





PGDMP      0        
         }         
   foodrichdb    16.6    17.2 m    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                           false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                           false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                           false            �           1262    17305 
   foodrichdb    DATABASE     �   CREATE DATABASE foodrichdb WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Chinese (Traditional)_Taiwan.950';
    DROP DATABASE foodrichdb;
                     postgres    false                        3079    17306    dblink 	   EXTENSION     :   CREATE EXTENSION IF NOT EXISTS dblink WITH SCHEMA public;
    DROP EXTENSION dblink;
                        false            �           0    0    EXTENSION dblink    COMMENT     _   COMMENT ON EXTENSION dblink IS 'connect to other PostgreSQL databases from within a database';
                             false    2                       1255    17586    update_restaurant_score()    FUNCTION       CREATE FUNCTION public.update_restaurant_score() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF TG_OP = 'INSERT' OR TG_OP = 'UPDATE' THEN
        -- 計算新的平均分數
        UPDATE restaurants
        SET score = (
            SELECT ROUND(AVG(rating)::NUMERIC, 2) 
            FROM reviews 
            WHERE restaurant_id = NEW.restaurant_id
        )
        WHERE restaurant_id = NEW.restaurant_id;
    ELSIF TG_OP = 'DELETE' THEN
        -- 刪除後重新計算平均分數
        UPDATE restaurants
        SET score = (
            SELECT ROUND(AVG(rating)::NUMERIC, 2) 
            FROM reviews 
            WHERE restaurant_id = OLD.restaurant_id
        )
        WHERE restaurant_id = OLD.restaurant_id;
    END IF;
    RETURN NULL;
END;
$$;
 0   DROP FUNCTION public.update_restaurant_score();
       public               postgres    false            �            1259    17352    reviews    TABLE     Y  CREATE TABLE public.reviews (
    review_id integer NOT NULL,
    restaurant_id integer,
    user_id integer,
    rating numeric(2,1),
    content text,
    created_at timestamp with time zone,
    store_id integer,
    is_flagged boolean DEFAULT false,
    is_approved boolean,
    CONSTRAINT chk_rating_max CHECK ((rating <= (5)::numeric))
);
    DROP TABLE public.reviews;
       public         heap r       postgres    false            �            1259    17358    Reviews_review_id_seq    SEQUENCE     �   CREATE SEQUENCE public."Reviews_review_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public."Reviews_review_id_seq";
       public               postgres    false    217            �           0    0    Reviews_review_id_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public."Reviews_review_id_seq" OWNED BY public.reviews.review_id;
          public               postgres    false    218            �            1259    17359    Reviews_review_id_seq1    SEQUENCE     �   ALTER TABLE public.reviews ALTER COLUMN review_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Reviews_review_id_seq1"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    217            �            1259    17360    admin    TABLE     �   CREATE TABLE public.admin (
    admin_id integer NOT NULL,
    admin_account character varying(100) NOT NULL,
    password character varying(255) NOT NULL
);
    DROP TABLE public.admin;
       public         heap r       postgres    false            �            1259    17363    admin_admin_id_seq    SEQUENCE     �   ALTER TABLE public.admin ALTER COLUMN admin_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.admin_admin_id_seq
    START WITH 3
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    220            �            1259    17364    browsing_history    TABLE     �   CREATE TABLE public.browsing_history (
    history_id integer NOT NULL,
    user_id integer NOT NULL,
    restaurant_id integer NOT NULL,
    viewed_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);
 $   DROP TABLE public.browsing_history;
       public         heap r       postgres    false            �            1259    17368    browsing_history_history_id_seq    SEQUENCE     �   CREATE SEQUENCE public.browsing_history_history_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 6   DROP SEQUENCE public.browsing_history_history_id_seq;
       public               postgres    false    222            �           0    0    browsing_history_history_id_seq    SEQUENCE OWNED BY     c   ALTER SEQUENCE public.browsing_history_history_id_seq OWNED BY public.browsing_history.history_id;
          public               postgres    false    223            �            1259    17369    business_hours    TABLE     �   CREATE TABLE public.business_hours (
    restaurant_id integer NOT NULL,
    day_of_week character varying(20) NOT NULL,
    start_time time with time zone NOT NULL,
    end_time time with time zone
);
 "   DROP TABLE public.business_hours;
       public         heap r       postgres    false            �            1259    17372 
   categories    TABLE     n   CREATE TABLE public.categories (
    category_id integer NOT NULL,
    name character varying(50) NOT NULL
);
    DROP TABLE public.categories;
       public         heap r       postgres    false            �            1259    17375    coupons    TABLE     �   CREATE TABLE public.coupons (
    coupon_id integer NOT NULL,
    restaurant_id integer,
    name character varying(255) NOT NULL,
    description text,
    created_at timestamp without time zone,
    store_id integer
);
    DROP TABLE public.coupons;
       public         heap r       postgres    false            �            1259    17380    coupons_coupon_id_seq    SEQUENCE     �   ALTER TABLE public.coupons ALTER COLUMN coupon_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.coupons_coupon_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    226            �            1259    17381    coupons_orders    TABLE     �   CREATE TABLE public.coupons_orders (
    order_id integer NOT NULL,
    coupon_id integer,
    user_id integer,
    restaurant_id integer,
    quantity integer,
    total_price numeric(10,2),
    store_id integer
);
 "   DROP TABLE public.coupons_orders;
       public         heap r       postgres    false            �            1259    17384    coupons_orders_order_id_seq    SEQUENCE     �   ALTER TABLE public.coupons_orders ALTER COLUMN order_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.coupons_orders_order_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    228            �            1259    17385    favorite_restaurants    TABLE        CREATE TABLE public.favorite_restaurants (
    favorite_id integer NOT NULL,
    user_id integer,
    restaurant_id integer
);
 (   DROP TABLE public.favorite_restaurants;
       public         heap r       postgres    false            �            1259    17388 $   favorite_restaurants_favorite_id_seq    SEQUENCE     �   ALTER TABLE public.favorite_restaurants ALTER COLUMN favorite_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.favorite_restaurants_favorite_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    230            �            1259    17389    reservations    TABLE     ?  CREATE TABLE public.reservations (
    reservation_id integer NOT NULL,
    user_id integer NOT NULL,
    reservation_date date NOT NULL,
    reservation_time time with time zone NOT NULL,
    num_people smallint NOT NULL,
    edit_time time with time zone NOT NULL,
    state boolean NOT NULL,
    store_id integer
);
     DROP TABLE public.reservations;
       public         heap r       postgres    false            �            1259    17392    reservations_reservation_id_seq    SEQUENCE     �   ALTER TABLE public.reservations ALTER COLUMN reservation_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.reservations_reservation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    232            �            1259    17393    restaurant_categories    TABLE     t   CREATE TABLE public.restaurant_categories (
    restaurant_id integer NOT NULL,
    category_id integer NOT NULL
);
 )   DROP TABLE public.restaurant_categories;
       public         heap r       postgres    false            �            1259    17396    restaurants    TABLE     �  CREATE TABLE public.restaurants (
    restaurant_id integer NOT NULL,
    name character varying(255) NOT NULL,
    description text,
    country character varying(100) NOT NULL,
    district character varying(100) NOT NULL,
    address character varying(255) NOT NULL,
    score numeric(3,2) NOT NULL,
    average integer NOT NULL,
    image character varying(255) NOT NULL,
    phone character varying(20) NOT NULL,
    store_id integer,
    CONSTRAINT chk_score_max CHECK ((score <= (5)::numeric))
);
    DROP TABLE public.restaurants;
       public         heap r       postgres    false            �            1259    17401    restaurants_id_seq    SEQUENCE     {   CREATE SEQUENCE public.restaurants_id_seq
    START WITH 7
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.restaurants_id_seq;
       public               postgres    false            �            1259    17402    restaurants_restaurant_id_seq    SEQUENCE     �   ALTER TABLE public.restaurants ALTER COLUMN restaurant_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.restaurants_restaurant_id_seq
    START WITH 8
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    235            �            1259    17403    review_audits    TABLE     �   CREATE TABLE public.review_audits (
    audit_id integer NOT NULL,
    review_id integer,
    admin_id integer,
    action character varying(50) NOT NULL,
    reason text,
    is_final boolean DEFAULT false
);
 !   DROP TABLE public.review_audits;
       public         heap r       postgres    false            �            1259    17409    review_audits_audit_id_seq    SEQUENCE     �   ALTER TABLE public.review_audits ALTER COLUMN audit_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.review_audits_audit_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    238            �            1259    17410    store    TABLE     �   CREATE TABLE public.store (
    store_id integer NOT NULL,
    restaurant_id integer,
    store_account character varying,
    password character varying
);
    DROP TABLE public.store;
       public         heap r       postgres    false            �            1259    17415    store_store_id_seq    SEQUENCE     �   ALTER TABLE public.store ALTER COLUMN store_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.store_store_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    240            �            1259    17416    users    TABLE     <  CREATE TABLE public.users (
    user_id integer NOT NULL,
    name character varying(50) NOT NULL,
    users_account character varying(100) NOT NULL,
    password character varying(255) NOT NULL,
    tel character varying(50) NOT NULL,
    email character varying(255) NOT NULL,
    icon bytea,
    birthday date
);
    DROP TABLE public.users;
       public         heap r       postgres    false            �            1259    17421    users_user_id_seq    SEQUENCE     �   ALTER TABLE public.users ALTER COLUMN user_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.users_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    242            �           2604    17422    browsing_history history_id    DEFAULT     �   ALTER TABLE ONLY public.browsing_history ALTER COLUMN history_id SET DEFAULT nextval('public.browsing_history_history_id_seq'::regclass);
 J   ALTER TABLE public.browsing_history ALTER COLUMN history_id DROP DEFAULT;
       public               postgres    false    223    222            �          0    17360    admin 
   TABLE DATA           B   COPY public.admin (admin_id, admin_account, password) FROM stdin;
    public               postgres    false    220   ~�       �          0    17364    browsing_history 
   TABLE DATA           Y   COPY public.browsing_history (history_id, user_id, restaurant_id, viewed_at) FROM stdin;
    public               postgres    false    222   ��       �          0    17369    business_hours 
   TABLE DATA           Z   COPY public.business_hours (restaurant_id, day_of_week, start_time, end_time) FROM stdin;
    public               postgres    false    224   J�       �          0    17372 
   categories 
   TABLE DATA           7   COPY public.categories (category_id, name) FROM stdin;
    public               postgres    false    225   q�       �          0    17375    coupons 
   TABLE DATA           d   COPY public.coupons (coupon_id, restaurant_id, name, description, created_at, store_id) FROM stdin;
    public               postgres    false    226   ˑ       �          0    17381    coupons_orders 
   TABLE DATA           v   COPY public.coupons_orders (order_id, coupon_id, user_id, restaurant_id, quantity, total_price, store_id) FROM stdin;
    public               postgres    false    228   В       �          0    17385    favorite_restaurants 
   TABLE DATA           S   COPY public.favorite_restaurants (favorite_id, user_id, restaurant_id) FROM stdin;
    public               postgres    false    230   �       �          0    17389    reservations 
   TABLE DATA           �   COPY public.reservations (reservation_id, user_id, reservation_date, reservation_time, num_people, edit_time, state, store_id) FROM stdin;
    public               postgres    false    232   B�       �          0    17393    restaurant_categories 
   TABLE DATA           K   COPY public.restaurant_categories (restaurant_id, category_id) FROM stdin;
    public               postgres    false    234   ��       �          0    17396    restaurants 
   TABLE DATA           �   COPY public.restaurants (restaurant_id, name, description, country, district, address, score, average, image, phone, store_id) FROM stdin;
    public               postgres    false    235   �       �          0    17403    review_audits 
   TABLE DATA           `   COPY public.review_audits (audit_id, review_id, admin_id, action, reason, is_final) FROM stdin;
    public               postgres    false    238   �       �          0    17352    reviews 
   TABLE DATA           �   COPY public.reviews (review_id, restaurant_id, user_id, rating, content, created_at, store_id, is_flagged, is_approved) FROM stdin;
    public               postgres    false    217   ��       �          0    17410    store 
   TABLE DATA           Q   COPY public.store (store_id, restaurant_id, store_account, password) FROM stdin;
    public               postgres    false    240   e�       �          0    17416    users 
   TABLE DATA           c   COPY public.users (user_id, name, users_account, password, tel, email, icon, birthday) FROM stdin;
    public               postgres    false    242   �       �           0    0    Reviews_review_id_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public."Reviews_review_id_seq"', 1, false);
          public               postgres    false    218            �           0    0    Reviews_review_id_seq1    SEQUENCE SET     F   SELECT pg_catalog.setval('public."Reviews_review_id_seq1"', 8, true);
          public               postgres    false    219            �           0    0    admin_admin_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.admin_admin_id_seq', 4, true);
          public               postgres    false    221            �           0    0    browsing_history_history_id_seq    SEQUENCE SET     M   SELECT pg_catalog.setval('public.browsing_history_history_id_seq', 3, true);
          public               postgres    false    223            �           0    0    coupons_coupon_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.coupons_coupon_id_seq', 4, true);
          public               postgres    false    227            �           0    0    coupons_orders_order_id_seq    SEQUENCE SET     I   SELECT pg_catalog.setval('public.coupons_orders_order_id_seq', 3, true);
          public               postgres    false    229            �           0    0 $   favorite_restaurants_favorite_id_seq    SEQUENCE SET     R   SELECT pg_catalog.setval('public.favorite_restaurants_favorite_id_seq', 3, true);
          public               postgres    false    231            �           0    0    reservations_reservation_id_seq    SEQUENCE SET     M   SELECT pg_catalog.setval('public.reservations_reservation_id_seq', 2, true);
          public               postgres    false    233            �           0    0    restaurants_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.restaurants_id_seq', 7, true);
          public               postgres    false    236            �           0    0    restaurants_restaurant_id_seq    SEQUENCE SET     L   SELECT pg_catalog.setval('public.restaurants_restaurant_id_seq', 8, false);
          public               postgres    false    237            �           0    0    review_audits_audit_id_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('public.review_audits_audit_id_seq', 4, true);
          public               postgres    false    239            �           0    0    store_store_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.store_store_id_seq', 6, true);
          public               postgres    false    241            �           0    0    users_user_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.users_user_id_seq', 3, true);
          public               postgres    false    243            �           2606    17424    admin  account 
   CONSTRAINT     T   ALTER TABLE ONLY public.admin
    ADD CONSTRAINT " account" UNIQUE (admin_account);
 :   ALTER TABLE ONLY public.admin DROP CONSTRAINT " account";
       public                 postgres    false    220            �           2606    17426    coupons  coupon_id 
   CONSTRAINT     Y   ALTER TABLE ONLY public.coupons
    ADD CONSTRAINT " coupon_id" PRIMARY KEY (coupon_id);
 >   ALTER TABLE ONLY public.coupons DROP CONSTRAINT " coupon_id";
       public                 postgres    false    226            �           2606    17428    reviews  review_id 
   CONSTRAINT     Y   ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT " review_id" PRIMARY KEY (review_id);
 >   ALTER TABLE ONLY public.reviews DROP CONSTRAINT " review_id";
       public                 postgres    false    217            �           2606    17430    users  user_id 
   CONSTRAINT     S   ALTER TABLE ONLY public.users
    ADD CONSTRAINT " user_id" PRIMARY KEY (user_id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT " user_id";
       public                 postgres    false    242            �           2606    17432 +   favorite_restaurants  user_id_restaurant_id 
   CONSTRAINT     z   ALTER TABLE ONLY public.favorite_restaurants
    ADD CONSTRAINT " user_id_restaurant_id" UNIQUE (user_id, restaurant_id);
 W   ALTER TABLE ONLY public.favorite_restaurants DROP CONSTRAINT " user_id_restaurant_id";
       public                 postgres    false    230    230            �           2606    17434 	   users acc 
   CONSTRAINT     M   ALTER TABLE ONLY public.users
    ADD CONSTRAINT acc UNIQUE (users_account);
 3   ALTER TABLE ONLY public.users DROP CONSTRAINT acc;
       public                 postgres    false    242            �           2606    17436    admin admin_id 
   CONSTRAINT     R   ALTER TABLE ONLY public.admin
    ADD CONSTRAINT admin_id PRIMARY KEY (admin_id);
 8   ALTER TABLE ONLY public.admin DROP CONSTRAINT admin_id;
       public                 postgres    false    220            �           2606    17438    review_audits audit_id 
   CONSTRAINT     Z   ALTER TABLE ONLY public.review_audits
    ADD CONSTRAINT audit_id PRIMARY KEY (audit_id);
 @   ALTER TABLE ONLY public.review_audits DROP CONSTRAINT audit_id;
       public                 postgres    false    238            �           2606    17440 &   browsing_history browsing_history_pkey 
   CONSTRAINT     l   ALTER TABLE ONLY public.browsing_history
    ADD CONSTRAINT browsing_history_pkey PRIMARY KEY (history_id);
 P   ALTER TABLE ONLY public.browsing_history DROP CONSTRAINT browsing_history_pkey;
       public                 postgres    false    222            �           2606    17442 "   business_hours business_hours_pkey 
   CONSTRAINT     �   ALTER TABLE ONLY public.business_hours
    ADD CONSTRAINT business_hours_pkey PRIMARY KEY (restaurant_id, day_of_week, start_time);
 L   ALTER TABLE ONLY public.business_hours DROP CONSTRAINT business_hours_pkey;
       public                 postgres    false    224    224    224            �           2606    17444    categories categories_pkey 
   CONSTRAINT     a   ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (category_id);
 D   ALTER TABLE ONLY public.categories DROP CONSTRAINT categories_pkey;
       public                 postgres    false    225            �           2606    17446     favorite_restaurants favorite_id 
   CONSTRAINT     g   ALTER TABLE ONLY public.favorite_restaurants
    ADD CONSTRAINT favorite_id PRIMARY KEY (favorite_id);
 J   ALTER TABLE ONLY public.favorite_restaurants DROP CONSTRAINT favorite_id;
       public                 postgres    false    230            �           2606    17448    coupons name 
   CONSTRAINT     G   ALTER TABLE ONLY public.coupons
    ADD CONSTRAINT name UNIQUE (name);
 6   ALTER TABLE ONLY public.coupons DROP CONSTRAINT name;
       public                 postgres    false    226            �           2606    17450    coupons_orders order_id 
   CONSTRAINT     [   ALTER TABLE ONLY public.coupons_orders
    ADD CONSTRAINT order_id PRIMARY KEY (order_id);
 A   ALTER TABLE ONLY public.coupons_orders DROP CONSTRAINT order_id;
       public                 postgres    false    228            �           2606    17452    reservations reservation_id 
   CONSTRAINT     e   ALTER TABLE ONLY public.reservations
    ADD CONSTRAINT reservation_id PRIMARY KEY (reservation_id);
 E   ALTER TABLE ONLY public.reservations DROP CONSTRAINT reservation_id;
       public                 postgres    false    232            �           2606    17454 0   restaurant_categories restaurant_categories_pkey 
   CONSTRAINT     �   ALTER TABLE ONLY public.restaurant_categories
    ADD CONSTRAINT restaurant_categories_pkey PRIMARY KEY (restaurant_id, category_id);
 Z   ALTER TABLE ONLY public.restaurant_categories DROP CONSTRAINT restaurant_categories_pkey;
       public                 postgres    false    234    234            �           2606    17456    store restaurant_id 
   CONSTRAINT     W   ALTER TABLE ONLY public.store
    ADD CONSTRAINT restaurant_id UNIQUE (restaurant_id);
 =   ALTER TABLE ONLY public.store DROP CONSTRAINT restaurant_id;
       public                 postgres    false    240            �           2606    17458    restaurants restaurants_pkey 
   CONSTRAINT     e   ALTER TABLE ONLY public.restaurants
    ADD CONSTRAINT restaurants_pkey PRIMARY KEY (restaurant_id);
 F   ALTER TABLE ONLY public.restaurants DROP CONSTRAINT restaurants_pkey;
       public                 postgres    false    235            �           2606    17460    store store_id 
   CONSTRAINT     R   ALTER TABLE ONLY public.store
    ADD CONSTRAINT store_id PRIMARY KEY (store_id);
 8   ALTER TABLE ONLY public.store DROP CONSTRAINT store_id;
       public                 postgres    false    240            �           2606    17462 	   users tel 
   CONSTRAINT     C   ALTER TABLE ONLY public.users
    ADD CONSTRAINT tel UNIQUE (tel);
 3   ALTER TABLE ONLY public.users DROP CONSTRAINT tel;
       public                 postgres    false    242            �           2606    17464    store username 
   CONSTRAINT     R   ALTER TABLE ONLY public.store
    ADD CONSTRAINT username UNIQUE (store_account);
 8   ALTER TABLE ONLY public.store DROP CONSTRAINT username;
       public                 postgres    false    240            �           1259    17465     idx_business_hours_restaurant_id    INDEX     d   CREATE INDEX idx_business_hours_restaurant_id ON public.business_hours USING btree (restaurant_id);
 4   DROP INDEX public.idx_business_hours_restaurant_id;
       public                 postgres    false    224            �           1259    17466 %   idx_restaurant_categories_category_id    INDEX     n   CREATE INDEX idx_restaurant_categories_category_id ON public.restaurant_categories USING btree (category_id);
 9   DROP INDEX public.idx_restaurant_categories_category_id;
       public                 postgres    false    234            �           1259    17467 '   idx_restaurant_categories_restaurant_id    INDEX     r   CREATE INDEX idx_restaurant_categories_restaurant_id ON public.restaurant_categories USING btree (restaurant_id);
 ;   DROP INDEX public.idx_restaurant_categories_restaurant_id;
       public                 postgres    false    234                       2620    17587 #   reviews trg_update_restaurant_score    TRIGGER     �   CREATE TRIGGER trg_update_restaurant_score AFTER INSERT OR DELETE OR UPDATE ON public.reviews FOR EACH ROW EXECUTE FUNCTION public.update_restaurant_score();
 <   DROP TRIGGER trg_update_restaurant_score ON public.reviews;
       public               postgres    false    217    285            �           2606    17468 4   browsing_history browsing_history_restaurant_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.browsing_history
    ADD CONSTRAINT browsing_history_restaurant_id_fkey FOREIGN KEY (restaurant_id) REFERENCES public.restaurants(restaurant_id);
 ^   ALTER TABLE ONLY public.browsing_history DROP CONSTRAINT browsing_history_restaurant_id_fkey;
       public               postgres    false    4836    222    235            �           2606    17473 .   browsing_history browsing_history_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.browsing_history
    ADD CONSTRAINT browsing_history_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id);
 X   ALTER TABLE ONLY public.browsing_history DROP CONSTRAINT browsing_history_user_id_fkey;
       public               postgres    false    4846    222    242                       2606    17478    review_audits fk_admin_id     FK CONSTRAINT     �   ALTER TABLE ONLY public.review_audits
    ADD CONSTRAINT "fk_admin_id " FOREIGN KEY (admin_id) REFERENCES public.admin(admin_id);
 F   ALTER TABLE ONLY public.review_audits DROP CONSTRAINT "fk_admin_id ";
       public               postgres    false    4811    220    238            �           2606    17483 "   business_hours fk_bh_restaurant_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.business_hours
    ADD CONSTRAINT fk_bh_restaurant_id FOREIGN KEY (restaurant_id) REFERENCES public.restaurants(restaurant_id) ON UPDATE CASCADE ON DELETE CASCADE;
 L   ALTER TABLE ONLY public.business_hours DROP CONSTRAINT fk_bh_restaurant_id;
       public               postgres    false    224    4836    235            �           2606    17488    coupons_orders fk_coupon_id     FK CONSTRAINT     �   ALTER TABLE ONLY public.coupons_orders
    ADD CONSTRAINT "fk_coupon_id " FOREIGN KEY (coupon_id) REFERENCES public.coupons(coupon_id);
 H   ALTER TABLE ONLY public.coupons_orders DROP CONSTRAINT "fk_coupon_id ";
       public               postgres    false    226    228    4820                       2606    17493 '   restaurant_categories fk_rc_category_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.restaurant_categories
    ADD CONSTRAINT fk_rc_category_id FOREIGN KEY (category_id) REFERENCES public.categories(category_id) ON UPDATE CASCADE ON DELETE CASCADE;
 Q   ALTER TABLE ONLY public.restaurant_categories DROP CONSTRAINT fk_rc_category_id;
       public               postgres    false    4818    225    234                       2606    17498 )   restaurant_categories fk_rc_restaurant_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.restaurant_categories
    ADD CONSTRAINT fk_rc_restaurant_id FOREIGN KEY (restaurant_id) REFERENCES public.restaurants(restaurant_id) ON UPDATE CASCADE ON DELETE CASCADE;
 S   ALTER TABLE ONLY public.restaurant_categories DROP CONSTRAINT fk_rc_restaurant_id;
       public               postgres    false    234    235    4836            �           2606    17503    reviews fk_restaurant_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT fk_restaurant_id FOREIGN KEY (restaurant_id) REFERENCES public.restaurants(restaurant_id);
 B   ALTER TABLE ONLY public.reviews DROP CONSTRAINT fk_restaurant_id;
       public               postgres    false    235    217    4836            �           2606    17523 &   favorite_restaurants fk_restaurant_id     FK CONSTRAINT     �   ALTER TABLE ONLY public.favorite_restaurants
    ADD CONSTRAINT "fk_restaurant_id " FOREIGN KEY (restaurant_id) REFERENCES public.restaurants(restaurant_id);
 R   ALTER TABLE ONLY public.favorite_restaurants DROP CONSTRAINT "fk_restaurant_id ";
       public               postgres    false    235    4836    230                       2606    17528    store fk_restaurant_id     FK CONSTRAINT     �   ALTER TABLE ONLY public.store
    ADD CONSTRAINT "fk_restaurant_id " FOREIGN KEY (restaurant_id) REFERENCES public.restaurants(restaurant_id);
 C   ALTER TABLE ONLY public.store DROP CONSTRAINT "fk_restaurant_id ";
       public               postgres    false    240    235    4836                       2606    17533    review_audits fk_review_id     FK CONSTRAINT     �   ALTER TABLE ONLY public.review_audits
    ADD CONSTRAINT "fk_review_id " FOREIGN KEY (review_id) REFERENCES public.reviews(review_id);
 G   ALTER TABLE ONLY public.review_audits DROP CONSTRAINT "fk_review_id ";
       public               postgres    false    4807    238    217                       2606    17538    restaurants fk_store_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.restaurants
    ADD CONSTRAINT fk_store_id FOREIGN KEY (store_id) REFERENCES public.store(store_id) NOT VALID;
 A   ALTER TABLE ONLY public.restaurants DROP CONSTRAINT fk_store_id;
       public               postgres    false    4842    235    240            �           2606    17543    reviews fk_store_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT fk_store_id FOREIGN KEY (store_id) REFERENCES public.store(store_id) NOT VALID;
 =   ALTER TABLE ONLY public.reviews DROP CONSTRAINT fk_store_id;
       public               postgres    false    4842    240    217            �           2606    17548    coupons fk_store_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.coupons
    ADD CONSTRAINT fk_store_id FOREIGN KEY (store_id) REFERENCES public.store(store_id) NOT VALID;
 =   ALTER TABLE ONLY public.coupons DROP CONSTRAINT fk_store_id;
       public               postgres    false    240    226    4842            �           2606    17553    coupons_orders fk_store_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.coupons_orders
    ADD CONSTRAINT fk_store_id FOREIGN KEY (store_id) REFERENCES public.store(store_id) NOT VALID;
 D   ALTER TABLE ONLY public.coupons_orders DROP CONSTRAINT fk_store_id;
       public               postgres    false    240    228    4842            �           2606    17558    reservations fk_store_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.reservations
    ADD CONSTRAINT fk_store_id FOREIGN KEY (store_id) REFERENCES public.store(store_id) NOT VALID;
 B   ALTER TABLE ONLY public.reservations DROP CONSTRAINT fk_store_id;
       public               postgres    false    240    232    4842            �           2606    17563    reviews fk_user_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.users(user_id) NOT VALID;
 <   ALTER TABLE ONLY public.reviews DROP CONSTRAINT fk_user_id;
       public               postgres    false    4846    217    242                        2606    17568    reservations fk_user_id     FK CONSTRAINT     ~   ALTER TABLE ONLY public.reservations
    ADD CONSTRAINT "fk_user_id " FOREIGN KEY (user_id) REFERENCES public.users(user_id);
 D   ALTER TABLE ONLY public.reservations DROP CONSTRAINT "fk_user_id ";
       public               postgres    false    4846    232    242            �           2606    17573    coupons_orders fk_user_id     FK CONSTRAINT     �   ALTER TABLE ONLY public.coupons_orders
    ADD CONSTRAINT "fk_user_id " FOREIGN KEY (user_id) REFERENCES public.users(user_id);
 F   ALTER TABLE ONLY public.coupons_orders DROP CONSTRAINT "fk_user_id ";
       public               postgres    false    242    4846    228            �           2606    17578     favorite_restaurants fk_user_id     FK CONSTRAINT     �   ALTER TABLE ONLY public.favorite_restaurants
    ADD CONSTRAINT "fk_user_id " FOREIGN KEY (user_id) REFERENCES public.users(user_id);
 L   ALTER TABLE ONLY public.favorite_restaurants DROP CONSTRAINT "fk_user_id ";
       public               postgres    false    230    242    4846            �   n   x�3�LL����442615�2�,��/�435162�2��K-�%���;��Tz����Ee����UzF[�E�V�F����������r��qqq 
o�      �   >   x�3�4�4�4202�50�5�P04�2��20�2��Ș�d�L�����s#�S�=... I��      �     x���M
�0���UI���Y<���
]�t/�G{���i:�袋����Ǩ��w����υRG)�������BM�������./̴-d�ۓeJ&��f?3������͐�8zL��@O�����Ez<�N)�gO���b4f�. c �g�7���S/;_Q3���ˉ����3t>�;OzL��@����C3�3t>��U�	����{V�<MƝo�;M3�;�0��bRw�a�Ť��.�.�˒b4flv�ȸ양I�fG���B�?$ll.      �   J  x�M�YRA����Xt7�]<��"԰L�S���Ȱ)�r�=}3U��_*˟D���9��B	&;�U����%;t1���am�a�/��0f�7�;;�3�.������q&햘s���=���Fx]F!�慨z<�I�o����B�ُ�~�8�n�g��MfI���R�7������q�P3k��A�s̒���m�^�J���T@�JX�Q���{l�n�(�Ze�<j=���젏24[���yy�НY8�:C���W��'��C_%ݚcT<�}MCU<�A� ���+/��T��p8�|��"����\      �   �   x�3�4�|���d����^4u>�����-O�,ڲ�Y�Χ���M_�b�����L���_l��b��gk�<��y�ӎ
O����Z��u��]�-A�`�
����@m&���f
��V��V�F\F���O7v�\�jq��K�B��t���]:�����Ɔ
FPW�ps�q:���y�g�ؾ��獈�����Bm6�2�4�|�d哽�_Ξ��o���c����rSNc�=... #tE      �   5   x��� 0�7Ӏ�m��%��90�i���BES&6:�*׵�;$?'��      �      x�3�4�4�bN3.cNcNS�=... !��      �   D   x�M��	 !D��%2�k���w�����	�`�S.Bc&'�p�C��s/�ypy
���K�V3�#�s      �   I   x����0B�0LU�����(�� �@�D
U�0�c-Z�iI��U#��j\�_�7q��i$lއ�H��      �     x���[WWǯ�O���y���@9�� ���Z,Nr�q`$r�TIT��Z�o�A}M�j(&�]����q�IW������������C `�u���⥼�>��{�c��B�(�|�һ: kMpz��r��4�y��4V���v�@��P��䍊�S�����v�ܣ\���O��#�ґ_�&�(���i?Py�꟒>U��� N.���v�	C>������K��;U�l��2r"��ۻ9ĢF�HR���o=�$��� $2�B>.F�9)>'E�Qn��L��P ]Af!��E�S���tӥI��M'�h�泏xH��⣼�㋼�eN�ƥ��DZ�	<GZb�"'��P!�O�KɬW�6��(��(4�#��D�E������rC���Ty���ΖA�|@'���g�ʁ���Y
�-H�ځq�=Rߵ�Ȭ׹�k�ŉ����
t��:8�U�ล����8i��5umC�qG�z	���~��KZ�l��������ӵ���n���^��o�C���~](/�RF]#�1�2̄ݔd-\���m�P1/&LI�3��#R���|XbڔsřVL�I��\W6k�%��`�C�ac�9z�*	"��3>O(�L��ɩ��4�r�=AG�N'pš&��B@��v�VYZW��������^�|z��v��\�7z�/��p�+��`K�o@ZZ��nՕڲr���v�;��l�Q�v�c�\=�%��#���UZ��s��
*��H�����t�Q�?\`G�G���w�����(��Qﴍb�6�[���A1+�.OpҘ����hF`�<��'��p��ΖƼ�hj�,ļ�q�F�F#B�泙��;��?���b*+����S�0'��;RyILE��cS,&��y�PH�v6��É�P:�@0� )�1���m��\�u�.z�gw�ߖ���;��Ry�T��"������3������b_�v���׌\����m��<هvc{�s8�u"on+�{�l��+8�Wkj����m����|r�St_J��<�}��~��.΅��gf��+T��"A+͎J���1��f�hq6Ŗ<�i6C|�'@قVq|t�C��'��$�e�f�c�H:(��v.�`c&G)��N�&�3_�%#!�1�D\��Ԝ4������@އ:�j��7�g�.�k����x��o��kPi�ګ#��r��v���/{�^FP�J}	*�Ac��N.���Y�����2�Ka��C�(�B��gi�d��Rv�0e�r� 	�L����[P�t��q�8���8i�����KsR����F: �GJ!Z"'��Z�d�]^�=�X4}J0K�f����Xە�״�E��c�i��m���nj�ɿ�j����A���k0����}���ԛ�q�Л睓�����q�m�(�~��i���� S���l��`|�9��XΓ�����d�F�ϐ�"4�#�$��L$��� ��������F��q��u؝�B��q�PĔ�B_��ckD(�wC��/�b      �   �   x�3�4�gv<mo�|ں�麝Oׯ�_6�z���~O��5˞N�x�m���3_,������ˈ����E��l�*4�@L��fmy�5�Gh�1�1�@� ���ƕϧn{>sϋ@&�1~���qqq H�w�      �   �   x�3�4�4�4�3�|�0�ٌ�Ov4<m�~�����I�g��\2������4r����)�Z ���Pog	���1���!�)/:&=�Ӌ� 3�A�`���r�q>���y��O'4#�X*Z�XZ���r� ��Q��@/qS���F$z!F��� �Ò�      �   n   x��I� E�߇��`w�ƕ"J�H���]�� �=o}o�".�s �S�^j�dc98H���)8�`�%�G�1,~چJ���Q��t�?e�%y�!�[1�h}�>� �      �   t   x�3�t*JL�L�F�&�f����X�!=713G/9?�3Ə����@�����8�S�2�8��$\���	G�mhii�k`�kd�e�镟�Ǚ"B� a��� ��b���� 6�.�     
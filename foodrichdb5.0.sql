PGDMP  7                    }         
   richfooddb    16.6    17.2 �    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                           false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                           false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                           false            �           1262    17934 
   richfooddb    DATABASE     �   CREATE DATABASE richfooddb WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Chinese (Traditional)_Taiwan.950';
    DROP DATABASE richfooddb;
                     postgres    false                        3079    17935    dblink 	   EXTENSION     :   CREATE EXTENSION IF NOT EXISTS dblink WITH SCHEMA public;
    DROP EXTENSION dblink;
                        false            �           0    0    EXTENSION dblink    COMMENT     _   COMMENT ON EXTENSION dblink IS 'connect to other PostgreSQL databases from within a database';
                             false    2            &           1255    17981    update_restaurant_score()    FUNCTION       CREATE FUNCTION public.update_restaurant_score() RETURNS trigger
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
       public               postgres    false            �            1259    17982    reviews    TABLE     Y  CREATE TABLE public.reviews (
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
       public         heap r       postgres    false            �            1259    17989    Reviews_review_id_seq    SEQUENCE     �   CREATE SEQUENCE public."Reviews_review_id_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public."Reviews_review_id_seq";
       public               postgres    false    217            �           0    0    Reviews_review_id_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public."Reviews_review_id_seq" OWNED BY public.reviews.review_id;
          public               postgres    false    218            �            1259    17990    Reviews_review_id_seq1    SEQUENCE     �   ALTER TABLE public.reviews ALTER COLUMN review_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Reviews_review_id_seq1"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    217            �            1259    17991    admin    TABLE     �   CREATE TABLE public.admin (
    admin_id integer NOT NULL,
    admin_account character varying(100) NOT NULL,
    password character varying(255) NOT NULL
);
    DROP TABLE public.admin;
       public         heap r       postgres    false            �            1259    17994    admin_admin_id_seq    SEQUENCE     �   ALTER TABLE public.admin ALTER COLUMN admin_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.admin_admin_id_seq
    START WITH 3
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    220            �            1259    17995    browsing_history    TABLE     �   CREATE TABLE public.browsing_history (
    history_id integer NOT NULL,
    user_id integer NOT NULL,
    restaurant_id integer NOT NULL,
    viewed_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
);
 $   DROP TABLE public.browsing_history;
       public         heap r       postgres    false            �            1259    17999    browsing_history_history_id_seq    SEQUENCE     �   CREATE SEQUENCE public.browsing_history_history_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 6   DROP SEQUENCE public.browsing_history_history_id_seq;
       public               postgres    false    222            �           0    0    browsing_history_history_id_seq    SEQUENCE OWNED BY     c   ALTER SEQUENCE public.browsing_history_history_id_seq OWNED BY public.browsing_history.history_id;
          public               postgres    false    223            �            1259    18000    business_hours    TABLE     �   CREATE TABLE public.business_hours (
    restaurant_id integer NOT NULL,
    day_of_week character varying(20) NOT NULL,
    start_time time with time zone NOT NULL,
    end_time time with time zone
);
 "   DROP TABLE public.business_hours;
       public         heap r       postgres    false            �            1259    18228    business_hours_english    TABLE     �   CREATE TABLE public.business_hours_english (
    restaurant_id integer NOT NULL,
    day_of_week character varying(20) NOT NULL,
    start_time time with time zone NOT NULL,
    end_time time with time zone
);
 *   DROP TABLE public.business_hours_english;
       public         heap r       postgres    false            �            1259    18003 
   categories    TABLE     n   CREATE TABLE public.categories (
    category_id integer NOT NULL,
    name character varying(50) NOT NULL
);
    DROP TABLE public.categories;
       public         heap r       postgres    false            �            1259    18233    categories_english    TABLE     m   CREATE TABLE public.categories_english (
    category_id integer NOT NULL,
    name character varying(50)
);
 &   DROP TABLE public.categories_english;
       public         heap r       postgres    false            �            1259    18006    coupons    TABLE     �   CREATE TABLE public.coupons (
    coupon_id integer NOT NULL,
    name character varying(255) NOT NULL,
    description text,
    created_at timestamp without time zone,
    store_id integer,
    price numeric(10,2)
);
    DROP TABLE public.coupons;
       public         heap r       postgres    false            �            1259    18011    coupons_coupon_id_seq    SEQUENCE     �   ALTER TABLE public.coupons ALTER COLUMN coupon_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.coupons_coupon_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    226            �            1259    18012    coupons_orders    TABLE     �   CREATE TABLE public.coupons_orders (
    order_id integer NOT NULL,
    coupon_id integer,
    user_id integer,
    quantity integer,
    price numeric(10,2),
    store_id integer,
    status boolean DEFAULT false,
    total_price numeric(10,2)
);
 "   DROP TABLE public.coupons_orders;
       public         heap r       postgres    false            �            1259    18016    coupons_orders_order_id_seq    SEQUENCE     �   ALTER TABLE public.coupons_orders ALTER COLUMN order_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.coupons_orders_order_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    228            �            1259    18017    favorite_restaurants    TABLE        CREATE TABLE public.favorite_restaurants (
    favorite_id integer NOT NULL,
    user_id integer,
    restaurant_id integer
);
 (   DROP TABLE public.favorite_restaurants;
       public         heap r       postgres    false            �            1259    18020 $   favorite_restaurants_favorite_id_seq    SEQUENCE     �   ALTER TABLE public.favorite_restaurants ALTER COLUMN favorite_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.favorite_restaurants_favorite_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    230            �            1259    18260    history    TABLE     �   CREATE TABLE public.history (
    history_id integer NOT NULL,
    restaurant_id integer NOT NULL,
    viewed_at timestamp without time zone NOT NULL
);
    DROP TABLE public.history;
       public         heap r       postgres    false            �            1259    18021    reservations    TABLE     F  CREATE TABLE public.reservations (
    reservation_id integer NOT NULL,
    user_id integer NOT NULL,
    num_people smallint NOT NULL,
    state boolean NOT NULL,
    store_id integer NOT NULL,
    edit_time timestamp with time zone,
    reservation_time character varying(100),
    reservation_date character varying(50)
);
     DROP TABLE public.reservations;
       public         heap r       postgres    false            �            1259    18024    reservations_reservation_id_seq    SEQUENCE     �   ALTER TABLE public.reservations ALTER COLUMN reservation_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.reservations_reservation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    232            �            1259    18025    restaurant_capacity    TABLE     �   CREATE TABLE public.restaurant_capacity (
    capacity_id integer NOT NULL,
    "time" character varying(100) NOT NULL,
    max_capacity smallint NOT NULL,
    store_id integer,
    date character varying(100)
);
 '   DROP TABLE public.restaurant_capacity;
       public         heap r       postgres    false            �            1259    18028 #   restaurant_capacity_capacity_id_seq    SEQUENCE     �   CREATE SEQUENCE public.restaurant_capacity_capacity_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 :   DROP SEQUENCE public.restaurant_capacity_capacity_id_seq;
       public               postgres    false    234            �           0    0 #   restaurant_capacity_capacity_id_seq    SEQUENCE OWNED BY     k   ALTER SEQUENCE public.restaurant_capacity_capacity_id_seq OWNED BY public.restaurant_capacity.capacity_id;
          public               postgres    false    235            �            1259    18029 $   restaurant_capacity_capacity_id_seq1    SEQUENCE     �   ALTER TABLE public.restaurant_capacity ALTER COLUMN capacity_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.restaurant_capacity_capacity_id_seq1
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    234            �            1259    18030    restaurant_categories    TABLE     t   CREATE TABLE public.restaurant_categories (
    restaurant_id integer NOT NULL,
    category_id integer NOT NULL
);
 )   DROP TABLE public.restaurant_categories;
       public         heap r       postgres    false            �            1259    18243    restaurant_categories_english    TABLE     |   CREATE TABLE public.restaurant_categories_english (
    restaurant_id integer NOT NULL,
    category_id integer NOT NULL
);
 1   DROP TABLE public.restaurant_categories_english;
       public         heap r       postgres    false            �            1259    18033    restaurants    TABLE     7  CREATE TABLE public.restaurants (
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
    longitude double precision,
    latitude double precision,
    CONSTRAINT chk_score_max CHECK ((score <= (5)::numeric))
);
    DROP TABLE public.restaurants;
       public         heap r       postgres    false            �            1259    18215    restaurants_english    TABLE     �  CREATE TABLE public.restaurants_english (
    restaurant_id integer NOT NULL,
    name character varying(255),
    description text,
    country character varying(100),
    district character varying(100),
    address character varying(255),
    score numeric(3,2),
    average integer,
    image character varying(255),
    phone character varying(20),
    longitude double precision,
    latitude double precision
);
 '   DROP TABLE public.restaurants_english;
       public         heap r       postgres    false            �            1259    18227 %   restaurants_english_restaurant_id_seq    SEQUENCE     �   ALTER TABLE public.restaurants_english ALTER COLUMN restaurant_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.restaurants_english_restaurant_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    247            �            1259    18039    restaurants_id_seq    SEQUENCE     {   CREATE SEQUENCE public.restaurants_id_seq
    START WITH 7
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.restaurants_id_seq;
       public               postgres    false            �            1259    18040    restaurants_restaurant_id_seq    SEQUENCE     �   ALTER TABLE public.restaurants ALTER COLUMN restaurant_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.restaurants_restaurant_id_seq
    START WITH 8
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    238            �            1259    18041    review_audits    TABLE     �   CREATE TABLE public.review_audits (
    audit_id integer NOT NULL,
    review_id integer,
    admin_id integer NOT NULL,
    action character varying(50) NOT NULL,
    reason text,
    is_final boolean DEFAULT false
);
 !   DROP TABLE public.review_audits;
       public         heap r       postgres    false            �            1259    18047    review_audits_audit_id_seq    SEQUENCE     �   ALTER TABLE public.review_audits ALTER COLUMN audit_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.review_audits_audit_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    241            �            1259    18048    store    TABLE     �   CREATE TABLE public.store (
    store_id integer NOT NULL,
    restaurant_id integer,
    store_account character varying,
    password character varying,
    icon text
);
    DROP TABLE public.store;
       public         heap r       postgres    false            �            1259    18053    store_store_id_seq    SEQUENCE     �   ALTER TABLE public.store ALTER COLUMN store_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.store_store_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    243            �            1259    18054    users    TABLE     p  CREATE TABLE public.users (
    user_id integer NOT NULL,
    name character varying(50) NOT NULL,
    users_account character varying(100) NOT NULL,
    password character varying(255) NOT NULL,
    tel character varying(50) NOT NULL,
    email character varying(255) NOT NULL,
    birthday date,
    gender character varying(50),
    icon character varying(1000)
);
    DROP TABLE public.users;
       public         heap r       postgres    false            �            1259    18059    users_user_id_seq    SEQUENCE     �   ALTER TABLE public.users ALTER COLUMN user_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.users_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    245            �           2604    18060    browsing_history history_id    DEFAULT     �   ALTER TABLE ONLY public.browsing_history ALTER COLUMN history_id SET DEFAULT nextval('public.browsing_history_history_id_seq'::regclass);
 J   ALTER TABLE public.browsing_history ALTER COLUMN history_id DROP DEFAULT;
       public               postgres    false    223    222            �          0    17991    admin 
   TABLE DATA           B   COPY public.admin (admin_id, admin_account, password) FROM stdin;
    public               postgres    false    220   	�       �          0    17995    browsing_history 
   TABLE DATA           Y   COPY public.browsing_history (history_id, user_id, restaurant_id, viewed_at) FROM stdin;
    public               postgres    false    222   �       �          0    18000    business_hours 
   TABLE DATA           Z   COPY public.business_hours (restaurant_id, day_of_week, start_time, end_time) FROM stdin;
    public               postgres    false    224   j�       �          0    18228    business_hours_english 
   TABLE DATA           b   COPY public.business_hours_english (restaurant_id, day_of_week, start_time, end_time) FROM stdin;
    public               postgres    false    249   ��       �          0    18003 
   categories 
   TABLE DATA           7   COPY public.categories (category_id, name) FROM stdin;
    public               postgres    false    225   �       �          0    18233    categories_english 
   TABLE DATA           ?   COPY public.categories_english (category_id, name) FROM stdin;
    public               postgres    false    250   ^�       �          0    18006    coupons 
   TABLE DATA           \   COPY public.coupons (coupon_id, name, description, created_at, store_id, price) FROM stdin;
    public               postgres    false    226   ̾       �          0    18012    coupons_orders 
   TABLE DATA           v   COPY public.coupons_orders (order_id, coupon_id, user_id, quantity, price, store_id, status, total_price) FROM stdin;
    public               postgres    false    228   п       �          0    18017    favorite_restaurants 
   TABLE DATA           S   COPY public.favorite_restaurants (favorite_id, user_id, restaurant_id) FROM stdin;
    public               postgres    false    230   �       �          0    18260    history 
   TABLE DATA           G   COPY public.history (history_id, restaurant_id, viewed_at) FROM stdin;
    public               postgres    false    252   I�       �          0    18021    reservations 
   TABLE DATA           �   COPY public.reservations (reservation_id, user_id, num_people, state, store_id, edit_time, reservation_time, reservation_date) FROM stdin;
    public               postgres    false    232   f�       �          0    18025    restaurant_capacity 
   TABLE DATA           `   COPY public.restaurant_capacity (capacity_id, "time", max_capacity, store_id, date) FROM stdin;
    public               postgres    false    234   ��       �          0    18030    restaurant_categories 
   TABLE DATA           K   COPY public.restaurant_categories (restaurant_id, category_id) FROM stdin;
    public               postgres    false    237   ��       �          0    18243    restaurant_categories_english 
   TABLE DATA           S   COPY public.restaurant_categories_english (restaurant_id, category_id) FROM stdin;
    public               postgres    false    251   N�       �          0    18033    restaurants 
   TABLE DATA           �   COPY public.restaurants (restaurant_id, name, description, country, district, address, score, average, image, phone, store_id, longitude, latitude) FROM stdin;
    public               postgres    false    238   ��       �          0    18215    restaurants_english 
   TABLE DATA           �   COPY public.restaurants_english (restaurant_id, name, description, country, district, address, score, average, image, phone, longitude, latitude) FROM stdin;
    public               postgres    false    247   *�       �          0    18041    review_audits 
   TABLE DATA           `   COPY public.review_audits (audit_id, review_id, admin_id, action, reason, is_final) FROM stdin;
    public               postgres    false    241   ��       �          0    17982    reviews 
   TABLE DATA           �   COPY public.reviews (review_id, restaurant_id, user_id, rating, content, created_at, store_id, is_flagged, is_approved) FROM stdin;
    public               postgres    false    217   w�       �          0    18048    store 
   TABLE DATA           W   COPY public.store (store_id, restaurant_id, store_account, password, icon) FROM stdin;
    public               postgres    false    243   ]�       �          0    18054    users 
   TABLE DATA           k   COPY public.users (user_id, name, users_account, password, tel, email, birthday, gender, icon) FROM stdin;
    public               postgres    false    245   ��       �           0    0    Reviews_review_id_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public."Reviews_review_id_seq"', 1, false);
          public               postgres    false    218            �           0    0    Reviews_review_id_seq1    SEQUENCE SET     G   SELECT pg_catalog.setval('public."Reviews_review_id_seq1"', 12, true);
          public               postgres    false    219            �           0    0    admin_admin_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.admin_admin_id_seq', 9, true);
          public               postgres    false    221            �           0    0    browsing_history_history_id_seq    SEQUENCE SET     M   SELECT pg_catalog.setval('public.browsing_history_history_id_seq', 3, true);
          public               postgres    false    223            �           0    0    coupons_coupon_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.coupons_coupon_id_seq', 4, true);
          public               postgres    false    227            �           0    0    coupons_orders_order_id_seq    SEQUENCE SET     I   SELECT pg_catalog.setval('public.coupons_orders_order_id_seq', 3, true);
          public               postgres    false    229            �           0    0 $   favorite_restaurants_favorite_id_seq    SEQUENCE SET     R   SELECT pg_catalog.setval('public.favorite_restaurants_favorite_id_seq', 3, true);
          public               postgres    false    231            �           0    0    reservations_reservation_id_seq    SEQUENCE SET     M   SELECT pg_catalog.setval('public.reservations_reservation_id_seq', 2, true);
          public               postgres    false    233            �           0    0 #   restaurant_capacity_capacity_id_seq    SEQUENCE SET     R   SELECT pg_catalog.setval('public.restaurant_capacity_capacity_id_seq', 1, false);
          public               postgres    false    235            �           0    0 $   restaurant_capacity_capacity_id_seq1    SEQUENCE SET     R   SELECT pg_catalog.setval('public.restaurant_capacity_capacity_id_seq1', 2, true);
          public               postgres    false    236            �           0    0 %   restaurants_english_restaurant_id_seq    SEQUENCE SET     S   SELECT pg_catalog.setval('public.restaurants_english_restaurant_id_seq', 8, true);
          public               postgres    false    248            �           0    0    restaurants_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.restaurants_id_seq', 7, true);
          public               postgres    false    239            �           0    0    restaurants_restaurant_id_seq    SEQUENCE SET     L   SELECT pg_catalog.setval('public.restaurants_restaurant_id_seq', 8, false);
          public               postgres    false    240            �           0    0    review_audits_audit_id_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('public.review_audits_audit_id_seq', 7, true);
          public               postgres    false    242                        0    0    store_store_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.store_store_id_seq', 6, true);
          public               postgres    false    244                       0    0    users_user_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.users_user_id_seq', 3, true);
          public               postgres    false    246            �           2606    18062    admin  account 
   CONSTRAINT     T   ALTER TABLE ONLY public.admin
    ADD CONSTRAINT " account" UNIQUE (admin_account);
 :   ALTER TABLE ONLY public.admin DROP CONSTRAINT " account";
       public                 postgres    false    220            �           2606    18064    coupons  coupon_id 
   CONSTRAINT     Y   ALTER TABLE ONLY public.coupons
    ADD CONSTRAINT " coupon_id" PRIMARY KEY (coupon_id);
 >   ALTER TABLE ONLY public.coupons DROP CONSTRAINT " coupon_id";
       public                 postgres    false    226            �           2606    18066    reviews  review_id 
   CONSTRAINT     Y   ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT " review_id" PRIMARY KEY (review_id);
 >   ALTER TABLE ONLY public.reviews DROP CONSTRAINT " review_id";
       public                 postgres    false    217                       2606    18068    users  user_id 
   CONSTRAINT     S   ALTER TABLE ONLY public.users
    ADD CONSTRAINT " user_id" PRIMARY KEY (user_id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT " user_id";
       public                 postgres    false    245            �           2606    18070 +   favorite_restaurants  user_id_restaurant_id 
   CONSTRAINT     z   ALTER TABLE ONLY public.favorite_restaurants
    ADD CONSTRAINT " user_id_restaurant_id" UNIQUE (user_id, restaurant_id);
 W   ALTER TABLE ONLY public.favorite_restaurants DROP CONSTRAINT " user_id_restaurant_id";
       public                 postgres    false    230    230                       2606    18072 	   users acc 
   CONSTRAINT     M   ALTER TABLE ONLY public.users
    ADD CONSTRAINT acc UNIQUE (users_account);
 3   ALTER TABLE ONLY public.users DROP CONSTRAINT acc;
       public                 postgres    false    245            �           2606    18074    admin admin_id 
   CONSTRAINT     R   ALTER TABLE ONLY public.admin
    ADD CONSTRAINT admin_id PRIMARY KEY (admin_id);
 8   ALTER TABLE ONLY public.admin DROP CONSTRAINT admin_id;
       public                 postgres    false    220                       2606    18076    review_audits audit_id 
   CONSTRAINT     Z   ALTER TABLE ONLY public.review_audits
    ADD CONSTRAINT audit_id PRIMARY KEY (audit_id);
 @   ALTER TABLE ONLY public.review_audits DROP CONSTRAINT audit_id;
       public                 postgres    false    241            �           2606    18078 &   browsing_history browsing_history_pkey 
   CONSTRAINT     l   ALTER TABLE ONLY public.browsing_history
    ADD CONSTRAINT browsing_history_pkey PRIMARY KEY (history_id);
 P   ALTER TABLE ONLY public.browsing_history DROP CONSTRAINT browsing_history_pkey;
       public                 postgres    false    222                       2606    18232 2   business_hours_english business_hours_english_pkey 
   CONSTRAINT     �   ALTER TABLE ONLY public.business_hours_english
    ADD CONSTRAINT business_hours_english_pkey PRIMARY KEY (restaurant_id, day_of_week, start_time);
 \   ALTER TABLE ONLY public.business_hours_english DROP CONSTRAINT business_hours_english_pkey;
       public                 postgres    false    249    249    249            �           2606    18080 "   business_hours business_hours_pkey 
   CONSTRAINT     �   ALTER TABLE ONLY public.business_hours
    ADD CONSTRAINT business_hours_pkey PRIMARY KEY (restaurant_id, day_of_week, start_time);
 L   ALTER TABLE ONLY public.business_hours DROP CONSTRAINT business_hours_pkey;
       public                 postgres    false    224    224    224                       2606    18237 *   categories_english categories_english_pkey 
   CONSTRAINT     q   ALTER TABLE ONLY public.categories_english
    ADD CONSTRAINT categories_english_pkey PRIMARY KEY (category_id);
 T   ALTER TABLE ONLY public.categories_english DROP CONSTRAINT categories_english_pkey;
       public                 postgres    false    250            �           2606    18082    categories categories_pkey 
   CONSTRAINT     a   ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (category_id);
 D   ALTER TABLE ONLY public.categories DROP CONSTRAINT categories_pkey;
       public                 postgres    false    225            �           2606    18084     favorite_restaurants favorite_id 
   CONSTRAINT     g   ALTER TABLE ONLY public.favorite_restaurants
    ADD CONSTRAINT favorite_id PRIMARY KEY (favorite_id);
 J   ALTER TABLE ONLY public.favorite_restaurants DROP CONSTRAINT favorite_id;
       public                 postgres    false    230                       2606    18264    history history_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.history
    ADD CONSTRAINT history_pkey PRIMARY KEY (history_id);
 >   ALTER TABLE ONLY public.history DROP CONSTRAINT history_pkey;
       public                 postgres    false    252            �           2606    18086    coupons name 
   CONSTRAINT     G   ALTER TABLE ONLY public.coupons
    ADD CONSTRAINT name UNIQUE (name);
 6   ALTER TABLE ONLY public.coupons DROP CONSTRAINT name;
       public                 postgres    false    226            �           2606    18088    coupons_orders order_id 
   CONSTRAINT     [   ALTER TABLE ONLY public.coupons_orders
    ADD CONSTRAINT order_id PRIMARY KEY (order_id);
 A   ALTER TABLE ONLY public.coupons_orders DROP CONSTRAINT order_id;
       public                 postgres    false    228            �           2606    18090    reservations reservation_id 
   CONSTRAINT     e   ALTER TABLE ONLY public.reservations
    ADD CONSTRAINT reservation_id PRIMARY KEY (reservation_id);
 E   ALTER TABLE ONLY public.reservations DROP CONSTRAINT reservation_id;
       public                 postgres    false    232            �           2606    18092 ,   restaurant_capacity restaurant_capacity_pkey 
   CONSTRAINT     s   ALTER TABLE ONLY public.restaurant_capacity
    ADD CONSTRAINT restaurant_capacity_pkey PRIMARY KEY (capacity_id);
 V   ALTER TABLE ONLY public.restaurant_capacity DROP CONSTRAINT restaurant_capacity_pkey;
       public                 postgres    false    234                       2606    18247 @   restaurant_categories_english restaurant_categories_english_pkey 
   CONSTRAINT     �   ALTER TABLE ONLY public.restaurant_categories_english
    ADD CONSTRAINT restaurant_categories_english_pkey PRIMARY KEY (restaurant_id, category_id);
 j   ALTER TABLE ONLY public.restaurant_categories_english DROP CONSTRAINT restaurant_categories_english_pkey;
       public                 postgres    false    251    251                        2606    18094 0   restaurant_categories restaurant_categories_pkey 
   CONSTRAINT     �   ALTER TABLE ONLY public.restaurant_categories
    ADD CONSTRAINT restaurant_categories_pkey PRIMARY KEY (restaurant_id, category_id);
 Z   ALTER TABLE ONLY public.restaurant_categories DROP CONSTRAINT restaurant_categories_pkey;
       public                 postgres    false    237    237                       2606    18096    store restaurant_id 
   CONSTRAINT     W   ALTER TABLE ONLY public.store
    ADD CONSTRAINT restaurant_id UNIQUE (restaurant_id);
 =   ALTER TABLE ONLY public.store DROP CONSTRAINT restaurant_id;
       public                 postgres    false    243                       2606    18226 ,   restaurants_english restaurants_english_pkey 
   CONSTRAINT     u   ALTER TABLE ONLY public.restaurants_english
    ADD CONSTRAINT restaurants_english_pkey PRIMARY KEY (restaurant_id);
 V   ALTER TABLE ONLY public.restaurants_english DROP CONSTRAINT restaurants_english_pkey;
       public                 postgres    false    247                       2606    18098    restaurants restaurants_pkey 
   CONSTRAINT     e   ALTER TABLE ONLY public.restaurants
    ADD CONSTRAINT restaurants_pkey PRIMARY KEY (restaurant_id);
 F   ALTER TABLE ONLY public.restaurants DROP CONSTRAINT restaurants_pkey;
       public                 postgres    false    238                       2606    18100    store store_id 
   CONSTRAINT     R   ALTER TABLE ONLY public.store
    ADD CONSTRAINT store_id PRIMARY KEY (store_id);
 8   ALTER TABLE ONLY public.store DROP CONSTRAINT store_id;
       public                 postgres    false    243                       2606    18102 	   users tel 
   CONSTRAINT     C   ALTER TABLE ONLY public.users
    ADD CONSTRAINT tel UNIQUE (tel);
 3   ALTER TABLE ONLY public.users DROP CONSTRAINT tel;
       public                 postgres    false    245            
           2606    18104    store username 
   CONSTRAINT     R   ALTER TABLE ONLY public.store
    ADD CONSTRAINT username UNIQUE (store_account);
 8   ALTER TABLE ONLY public.store DROP CONSTRAINT username;
       public                 postgres    false    243            �           1259    18105     idx_business_hours_restaurant_id    INDEX     d   CREATE INDEX idx_business_hours_restaurant_id ON public.business_hours USING btree (restaurant_id);
 4   DROP INDEX public.idx_business_hours_restaurant_id;
       public                 postgres    false    224            �           1259    18106 %   idx_restaurant_categories_category_id    INDEX     n   CREATE INDEX idx_restaurant_categories_category_id ON public.restaurant_categories USING btree (category_id);
 9   DROP INDEX public.idx_restaurant_categories_category_id;
       public                 postgres    false    237            �           1259    18107 '   idx_restaurant_categories_restaurant_id    INDEX     r   CREATE INDEX idx_restaurant_categories_restaurant_id ON public.restaurant_categories USING btree (restaurant_id);
 ;   DROP INDEX public.idx_restaurant_categories_restaurant_id;
       public                 postgres    false    237            4           2620    18108 #   reviews trg_update_restaurant_score    TRIGGER     �   CREATE TRIGGER trg_update_restaurant_score AFTER INSERT OR DELETE OR UPDATE ON public.reviews FOR EACH ROW EXECUTE FUNCTION public.update_restaurant_score();
 <   DROP TRIGGER trg_update_restaurant_score ON public.reviews;
       public               postgres    false    294    217                       2606    18109 4   browsing_history browsing_history_restaurant_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.browsing_history
    ADD CONSTRAINT browsing_history_restaurant_id_fkey FOREIGN KEY (restaurant_id) REFERENCES public.restaurants(restaurant_id);
 ^   ALTER TABLE ONLY public.browsing_history DROP CONSTRAINT browsing_history_restaurant_id_fkey;
       public               postgres    false    4866    238    222                       2606    18114 .   browsing_history browsing_history_user_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.browsing_history
    ADD CONSTRAINT browsing_history_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id);
 X   ALTER TABLE ONLY public.browsing_history DROP CONSTRAINT browsing_history_user_id_fkey;
       public               postgres    false    245    4876    222            -           2606    18119    review_audits fk_admin_id     FK CONSTRAINT     �   ALTER TABLE ONLY public.review_audits
    ADD CONSTRAINT "fk_admin_id " FOREIGN KEY (admin_id) REFERENCES public.admin(admin_id);
 F   ALTER TABLE ONLY public.review_audits DROP CONSTRAINT "fk_admin_id ";
       public               postgres    false    220    4839    241                        2606    18124 "   business_hours fk_bh_restaurant_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.business_hours
    ADD CONSTRAINT fk_bh_restaurant_id FOREIGN KEY (restaurant_id) REFERENCES public.restaurants(restaurant_id) ON UPDATE CASCADE ON DELETE CASCADE;
 L   ALTER TABLE ONLY public.business_hours DROP CONSTRAINT fk_bh_restaurant_id;
       public               postgres    false    238    224    4866            1           2606    18253 ,   restaurant_categories_english fk_category_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.restaurant_categories_english
    ADD CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES public.categories_english(category_id);
 V   ALTER TABLE ONLY public.restaurant_categories_english DROP CONSTRAINT fk_category_id;
       public               postgres    false    250    251    4886            "           2606    18129    coupons_orders fk_coupon_id     FK CONSTRAINT     �   ALTER TABLE ONLY public.coupons_orders
    ADD CONSTRAINT "fk_coupon_id " FOREIGN KEY (coupon_id) REFERENCES public.coupons(coupon_id);
 H   ALTER TABLE ONLY public.coupons_orders DROP CONSTRAINT "fk_coupon_id ";
       public               postgres    false    226    228    4848            *           2606    18134 '   restaurant_categories fk_rc_category_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.restaurant_categories
    ADD CONSTRAINT fk_rc_category_id FOREIGN KEY (category_id) REFERENCES public.categories(category_id) ON UPDATE CASCADE ON DELETE CASCADE;
 Q   ALTER TABLE ONLY public.restaurant_categories DROP CONSTRAINT fk_rc_category_id;
       public               postgres    false    4846    225    237            +           2606    18139 )   restaurant_categories fk_rc_restaurant_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.restaurant_categories
    ADD CONSTRAINT fk_rc_restaurant_id FOREIGN KEY (restaurant_id) REFERENCES public.restaurants(restaurant_id) ON UPDATE CASCADE ON DELETE CASCADE;
 S   ALTER TABLE ONLY public.restaurant_categories DROP CONSTRAINT fk_rc_restaurant_id;
       public               postgres    false    237    238    4866                       2606    18144    reviews fk_restaurant_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT fk_restaurant_id FOREIGN KEY (restaurant_id) REFERENCES public.restaurants(restaurant_id);
 B   ALTER TABLE ONLY public.reviews DROP CONSTRAINT fk_restaurant_id;
       public               postgres    false    4866    238    217            0           2606    18238 '   business_hours_english fk_restaurant_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.business_hours_english
    ADD CONSTRAINT fk_restaurant_id FOREIGN KEY (restaurant_id) REFERENCES public.restaurants_english(restaurant_id) NOT VALID;
 Q   ALTER TABLE ONLY public.business_hours_english DROP CONSTRAINT fk_restaurant_id;
       public               postgres    false    249    247    4882            2           2606    18248 .   restaurant_categories_english fk_restaurant_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.restaurant_categories_english
    ADD CONSTRAINT fk_restaurant_id FOREIGN KEY (restaurant_id) REFERENCES public.restaurants_english(restaurant_id);
 X   ALTER TABLE ONLY public.restaurant_categories_english DROP CONSTRAINT fk_restaurant_id;
       public               postgres    false    251    247    4882            3           2606    18265    history fk_restaurant_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.history
    ADD CONSTRAINT fk_restaurant_id FOREIGN KEY (restaurant_id) REFERENCES public.restaurants(restaurant_id);
 B   ALTER TABLE ONLY public.history DROP CONSTRAINT fk_restaurant_id;
       public               postgres    false    4866    238    252            %           2606    18149 &   favorite_restaurants fk_restaurant_id     FK CONSTRAINT     �   ALTER TABLE ONLY public.favorite_restaurants
    ADD CONSTRAINT "fk_restaurant_id " FOREIGN KEY (restaurant_id) REFERENCES public.restaurants(restaurant_id);
 R   ALTER TABLE ONLY public.favorite_restaurants DROP CONSTRAINT "fk_restaurant_id ";
       public               postgres    false    230    4866    238            /           2606    18154    store fk_restaurant_id     FK CONSTRAINT     �   ALTER TABLE ONLY public.store
    ADD CONSTRAINT "fk_restaurant_id " FOREIGN KEY (restaurant_id) REFERENCES public.restaurants(restaurant_id);
 C   ALTER TABLE ONLY public.store DROP CONSTRAINT "fk_restaurant_id ";
       public               postgres    false    243    238    4866            .           2606    18159    review_audits fk_review_id     FK CONSTRAINT     �   ALTER TABLE ONLY public.review_audits
    ADD CONSTRAINT "fk_review_id " FOREIGN KEY (review_id) REFERENCES public.reviews(review_id);
 G   ALTER TABLE ONLY public.review_audits DROP CONSTRAINT "fk_review_id ";
       public               postgres    false    217    4835    241            ,           2606    18164    restaurants fk_store_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.restaurants
    ADD CONSTRAINT fk_store_id FOREIGN KEY (store_id) REFERENCES public.store(store_id) NOT VALID;
 A   ALTER TABLE ONLY public.restaurants DROP CONSTRAINT fk_store_id;
       public               postgres    false    243    238    4872                       2606    18169    reviews fk_store_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT fk_store_id FOREIGN KEY (store_id) REFERENCES public.store(store_id) NOT VALID;
 =   ALTER TABLE ONLY public.reviews DROP CONSTRAINT fk_store_id;
       public               postgres    false    4872    217    243            !           2606    18174    coupons fk_store_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.coupons
    ADD CONSTRAINT fk_store_id FOREIGN KEY (store_id) REFERENCES public.store(store_id) NOT VALID;
 =   ALTER TABLE ONLY public.coupons DROP CONSTRAINT fk_store_id;
       public               postgres    false    226    243    4872            #           2606    18179    coupons_orders fk_store_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.coupons_orders
    ADD CONSTRAINT fk_store_id FOREIGN KEY (store_id) REFERENCES public.store(store_id) NOT VALID;
 D   ALTER TABLE ONLY public.coupons_orders DROP CONSTRAINT fk_store_id;
       public               postgres    false    243    4872    228            '           2606    18184    reservations fk_store_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.reservations
    ADD CONSTRAINT fk_store_id FOREIGN KEY (store_id) REFERENCES public.store(store_id) NOT VALID;
 B   ALTER TABLE ONLY public.reservations DROP CONSTRAINT fk_store_id;
       public               postgres    false    232    243    4872            )           2606    18189    restaurant_capacity fk_store_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.restaurant_capacity
    ADD CONSTRAINT fk_store_id FOREIGN KEY (store_id) REFERENCES public.store(store_id) NOT VALID;
 I   ALTER TABLE ONLY public.restaurant_capacity DROP CONSTRAINT fk_store_id;
       public               postgres    false    243    4872    234                       2606    18194    reviews fk_user_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.reviews
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.users(user_id) NOT VALID;
 <   ALTER TABLE ONLY public.reviews DROP CONSTRAINT fk_user_id;
       public               postgres    false    245    4876    217            (           2606    18199    reservations fk_user_id     FK CONSTRAINT     ~   ALTER TABLE ONLY public.reservations
    ADD CONSTRAINT "fk_user_id " FOREIGN KEY (user_id) REFERENCES public.users(user_id);
 D   ALTER TABLE ONLY public.reservations DROP CONSTRAINT "fk_user_id ";
       public               postgres    false    4876    245    232            $           2606    18204    coupons_orders fk_user_id     FK CONSTRAINT     �   ALTER TABLE ONLY public.coupons_orders
    ADD CONSTRAINT "fk_user_id " FOREIGN KEY (user_id) REFERENCES public.users(user_id);
 F   ALTER TABLE ONLY public.coupons_orders DROP CONSTRAINT "fk_user_id ";
       public               postgres    false    4876    245    228            &           2606    18209     favorite_restaurants fk_user_id     FK CONSTRAINT     �   ALTER TABLE ONLY public.favorite_restaurants
    ADD CONSTRAINT "fk_user_id " FOREIGN KEY (user_id) REFERENCES public.users(user_id);
 L   ALTER TABLE ONLY public.favorite_restaurants DROP CONSTRAINT "fk_user_id ";
       public               postgres    false    4876    245    230            �     x�5��r�0@�u����C�.a��"E��&j
�J��3u؞�7�<_JD�����Z��a6�mT�������Ĭ��� t}[]d�9Qܨ�!0,t���#޿�]cѩSm�0CRʑ8��n=T+7��G�.`s�U��T�lք;�\�s/��Х)<�Q���&�J��w���Y,�W"t^�Tm*n��>����EQZ�\�R��S��r���,�9y�H�u�`���S�]�MƓT���r�I�/c�^}      �   >   x�3�4�4�4202�50�5�P04�2��20�2��Ș�d�L�����s#�S�=... I��      �     x���M
�0���UI��ųx7..
�r�^<��&im�t&/�E��ǓY��{�y�Lʃ�ۈ&s?�.�����h�|�O̴-�ӛOFV���BR�J�(�	�Cz,=:���'�3�q�� =������0�ҿi�1%�(��[@FC��1v?�q�3L��|I���2/&�cT�Gaϐ��'�<��	=c�}� ϐ��{V�'<c��Ye���L��}X/og"^3�C1���ޠ��|f��.�y�EA1
3fv�h���#���y�������AV(      �   b   x�3�|6c��9��h�44�20 "mN0��2����CX͎N�j�ΞM�]Sp�1!��&D�ل7��f"�lJ��M�p�)n6%�ͦD�9F��� e��P      �   J  x�M�YRA����Xt7�]<��"԰L�S���Ȱ)�r�=}3U��_*˟D���9��B	&;�U����%;t1���am�a�/��0f�7�;;�3�.������q&햘s���=���Fx]F!�慨z<�I�o����B�ُ�~�8�n�g��MfI���R�7������q�P3k��A�s̒���m�^�J���T@�JX�Q���{l�n�(�Ze�<j=���젏24[���yy�НY8�:C���W��'��C_%ݚcT<�}MCU<�A� ���+/��T��p8�|��"����\      �   ^  x�UQ�N�0<�~�����<6)��<������Mb%]W�*_�ˁ���ٝ�<3l�K=Y�Ԩ��E��"�}/e�#�	�m�1�6D�p9y���
����`I���H�'�8�%��>̆��Ϙe����N���{:�p`U�6��03���Y��hEc��ưq��Ixm�^�4��Zm���p�{[^�.Y����[I�
j9]2���=լv�;�ְ�������"j+:��2�Y4J"��ֹ}w�c�E��
WŲ���5G��&�<q��e�w'�sؕ���:G3�������2x�E��	���s[[7i�>��j+
	���P3�T��G3�Eًs�^��� ���<      �   �   x�3�|���d����^4u>�����-O�,ڲ�Y�Χ���M_�b�����L���_l��b��gk�<��y�ӎ
O����Z��u��]�-A�`�
����@m&���f
��V��V�F�1~\F�O7v�\�js��K�B��t���]������Ɔ
FPg���a��TZ��Z䕟��b�
�7"�f�VCB�j�!�jΧKV>ٻ���y��&Q�r<��Ym7�4����� �      �   <   x�%��  C�s��5�����`#�Ҽ�KbW��>
���:�H��>�p/3{���      �      x�3�4�4�bN3.cNcNS�=... !��      �      x������ � �      �   ?   x�3�4�4�,�FF��F��&
��V�V��1~@�e�i�ib TgUg�]]� w�	      �   0   x�3�|6}�]�f6=[������Ӑ3Əˈ���YH�P�=... ��d      �   I   x����0B�0LU�����(�� �@�D
U�0�c-Z�iI��U#��j\�_�7q��i$lއ�H��      �   F   x�%���0�x��	3L�����8�ıb�X��c;A1b*�JE&��ײ<�����m���� ?'      �   v  x���[SZYǟ���w͹_�j�& ��X]Eq��W�<�Z��%NlEӉ(�ML��t���4_a���v�f�:UU{����_�H��A�#}w,V�R���ҋϥ3p]͛P��w/���.�RK>���⑶�����T��8qg������w���xۖٔ�݁�z�[��JyC<��˦tUT�ݐ���Ξ�Ñr�
Ԟ��\Ֆ���S�Ļ��;��;��{��)����~��|.�+��7{�B�[F��"�u�WJ���1���(�b$�f���=y�#Ƣ�]��2�tHX�FV�c!��pPYc>g������
�0a>k��St0���v7]����0���17��9{2i泴��Ş�}f���TAH���v��̜G��.pv���D����P'p�q�G�h�QGpr�h��I��������+��#�sk/������P��'�b�޿	��El�ցrV��FG>�IՊt��4��/?t�ّ�y,�.5�b�Bjlh܇���T�`�@-�o��6@�4�H#��_���&P���[�&W''��kJ	��DZ�8Bn�f��L&�=�0Ė��d:�;}s��尅�.�3qsNd��Ra�5���B���4Y�EH.K�����p��ш>��F�#V!��a@廌0р�5p�+#�#��Mp�a�\��*����58]���=(5� ��2S'��g]��)���s��� ��TWk���+��h�g�׵�J�!c�`��-��[
d41F���a'�hv.@�(�[�7���(�N·ͮ8�P���<�,��r�i4����ZIX�>�w�M��7=,��,9�$L����	��2��O��p&�#8r�p#�i�eiV� �M]l��ޭ����h�����'P_��˷o�������k�]�����!���j�QH&�'�#�8�F��(<� )�a9�֡݃BI���FQ��a�\���(�n���Â���R�R*��ʇpJ�V�^IܿV�mUぺ��ZJ�:��z�6�v��@����ϔ��`l�{�����Ci����8������͏�����8E%g��*�=�_	Dk���v�����47�����1l�;���\�i_����{uz)�Ϙ���q�3�Yk`��bbz�S�Hڗ�����M�[
��q65k^�#H�� y|� ��(z@�!���H�S��/�0T�_��w-��P|X�;��_W�ϗ`w:(~�*�Ci^ɥW���2߸���]�Lu�����4���O'��&�`aj�(��J���j���A|��'���a��_C|�ӫs8��Q�wm����N:�?�L�Ц~�̐֘`c��H:���Z�zY��i/�.p��R0=�MY�6{����>p<j�_��f(�a�_�����n�b�#`ɼ�z���2lE���V��
�v��\u�N	r������a�l�O�epT�d�N30�HKj]@���_��}��Z�Zz�j[Am��`0�oa��~?�F5p�Tȯ�!5�rg�=!G�}��r<��[)n�A�"�K���<�$�Vo82�;�|�7���Y�Lv5?�Ⱥ�(�"aOޚ֣�\��bI�0��C�(;6�{S���:�4����t�F��+      �   z  x���Ɏ�8���)���fZ�a�bS�R�'1$6$N�<M�K�X;P���*�("|b��d��pDs��N C`DS	p<C�G9	A�������d�7�h�4A�b����)�c�+A�~�1������)��P��Q� `�Q ��e�6.��8�b�RP ���&Џ0Au����+@N�9Go�!Khv�P��' -�n��rm���L��P,ET��s
��ְ$A�$I�;e��Gj#�4�Q���S�a�&?�M�L��O������M=U�.�q\�֩3�nB�\uq�NW����6:x;�h��E�`�T�p�s�\��9h�׼yἿ���������W��$H��*�)I� +R��U��-A��.K�Zӄ	>r���f��s��:��'�Ġ⾏o`O���Ǳ3�����ΊcSL�d���_%��@�S���0%\���?# ����c������B���	SN~r��u�@~Up)���fF��{fF=w�G�N����D�<��U�xRw�M�	��?z�w��-G��~*6�m��66�!#��=4�c����#"��f~=k�����\�MF�&ƻ�Td�0j4�P4��F�-C�k��L ��#����n�o7�)��q~N)�Ёf�p��C̩�I�%��Ō_(~�#" ���� �� ��$!��>��l��\݄J�lX�������6�M�:��2��Ӓh���	��^K#Gb%Q��m�6��\��E7��ط��!LLʩ�y��4�`�-��]�����%m7׭�nCW���,��d�qۊl�j���{�����g�-�(�S|ob �����t��}@R,��^��\�+'[�6�NԐ�g;��d!�h�[�x��p��D}��d�t���\�z�N̯��O�^fO��M:^⏐{��T~It�{E[�G�"g����^W�>��.��kzh��+f�$if���̋x.���_c��0����3̯�)E	Γ{�f���u�ԐUp��뼨�*UYJx��w,H�����������tt����#Tx�<fph#po��K�u�E�omV�r�������H��h���1Ŏ�\#�_\���M+�c��h�r3���aOZ���p1�]_��nv�|$���o�ÑfH�}wd���%<Az�z�W�	ު\�Ы&/�N�r�� ��$��\��r��]��د����Q�q9�+8�$��j�fʸ-���}gy��c��}D^��S/�y	"��Mэ/���x�������e\D�[k��b�/y���]��檸�A$Wk�r�v9{v��B���޶��G�1?<�z蚑3�&*Ռm�9������Ay8u������ �f�n�������L���ݨ�j�q?#A      �   �   x�3�4�gv<mo�|ں�麝Oׯ�_6�z���~O��5˞N�x�m���3_,������ˈ����E��l�*4�@L��fmy�5�Gh�1�1�@� ���ƕϧn{>sϋ@&�1~��r������Ŋ��@3�C4=�@��]ӟ���bY㳍M@�朆D)����� E��(      �   �   x���9
�@����)�%�7�1�Y�=���B!�q�N{��4YL��"�D�i��<����l���<�Q�J�ŰZ'�~{9��=�A�"/D4����=��K�+�QZ,'�C�";$� ��xZ��l7ɓ�6A�(TA��x�7F�%�c��	>؈/�M@�l|oH>m���QR/�D@ozD|Gh���yʢ�+��~�ϑ      �   v   x���� E����H|L����d$�Y��ޞ{5<^������y�Oe�qp�sI���ja�%۴�os ���C�$��Oj.�ݣ�7�޹�ɩˇ��`���a}(���<%      �   w   x�3�t*JL�L�F�&�f����X�!=713G/9?�����@���8c���ˈ�;�,3�3L��@�`q$--Mu�t�,�sz�g�qf����P&HU������=... �>0�     
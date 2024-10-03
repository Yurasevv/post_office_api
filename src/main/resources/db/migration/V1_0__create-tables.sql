CREATE TABLE post_office (
    postal_code PRIMARY KEY UNIQUE,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL
);

CREATE TABLE postal_item (
    id BIGSERIAL PRIMARY KEY,
    recipient_name VARCHAR(50) NOT NULL,
    type VARCHAR(20) NOT NULL CHECK (type IN ('LETTER', 'PARCEL', 'PACKAGE', 'POSTCARD')),
    recipient_postal_code INT NOT NULL,
    recipient_address VARCHAR(50) NOT NULL,
    status VARCHAR(30) NOT NULL CHECK (status IN ('REGISTERED', 'IN_TRANSIT', 'ARRIVED_AT_POST_OFFICE', 'DELIVERED'))
);

CREATE TABLE postal_movement (
    id BIGSERIAL PRIMARY KEY,

    postal_item INT
        REFERENCES postal_item (id)
        ON DELETE RESTRICT,

    post_office_postal_code INT NOT NULL
        REFERENCES post_office (postal_code)
        ON DELETE RESTRICT,

    arrival_time TIMESTAMP NULL,
    departure_time TIMESTAMP NULL
);

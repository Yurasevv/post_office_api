INSERT INTO post_office (postal_code, name, address) VALUES
                                                         (101000, 'Main Post Office', '101 Main St, City A'),
                                                         (102000, 'Branch Post Office 1', '12 Branch Rd, City A'),
                                                         (103000, 'Branch Post Office 2', '5 South St, City B');
INSERT INTO postal_item (recipient_name, type, recipient_postal_code, recipient_address, status) VALUES
                                                                                                     ('John Doe', 'PARCEL', 101000, '123 Elm St, City A', 'REGISTERED'),
                                                                                                     ('Jane Smith', 'LETTER', 102000, '456 Oak St, City B', 'REGISTERED'),
                                                                                                     ('Alice Johnson', 'POSTCARD', 103000, '789 Pine St, City C', 'REGISTERED');

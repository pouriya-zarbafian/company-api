INSERT INTO company(name, address, city, country, email, phone_number) VALUES('Nice Company', '1 Park Street', 'Nice', 'France', 'contact@company.com', '+33 321 321 11');
INSERT INTO company(name, address, city, country, email, phone_number) VALUES('Another Company', '45 rue de Paris', 'Bordeaux', 'France', 'info@another.com', '+33 5566 7788');
INSERT INTO owner(name) VALUES('John');
INSERT INTO owner(name) VALUES('Jane');
INSERT INTO owner(name) VALUES('Paul');
INSERT INTO owner(name) VALUES('Sophie');
INSERT INTO company_owner(company_id, owner_id) SELECT c.id, o.id FROM company c, owner o WHERE c.name = 'Nice Company' AND o.name='Paul';
INSERT INTO company_owner(company_id, owner_id) SELECT c.id, o.id FROM company c, owner o WHERE c.name = 'Nice Company' AND o.name='Jane';
INSERT INTO company_owner(company_id, owner_id) SELECT c.id, o.id FROM company c, owner o WHERE c.name = 'Another Company' AND o.name='Paul';


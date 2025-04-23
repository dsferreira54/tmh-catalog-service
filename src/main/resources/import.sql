-- Insert artisans
INSERT INTO Artisan (name, specialty) VALUES ('Alice', 'Felt Hats');
INSERT INTO Artisan (name, specialty) VALUES ('Bob', 'Wool Hats');

-- Insert hats associated with the artisans
-- Assuming 'Alice' was inserted first and 'Bob' second (relying on auto-increment order)
INSERT INTO Hat (name, color, artisan_id) VALUES ('Sunshine', 'Yellow', 1);
INSERT INTO Hat (name, color, artisan_id) VALUES ('Midnight', 'Black', 1);
INSERT INTO Hat (name, color, artisan_id) VALUES ('Snowcap', 'White', 2);
INSERT INTO Hat (name, color, artisan_id) VALUES ('Fedora', 'Red', 2);
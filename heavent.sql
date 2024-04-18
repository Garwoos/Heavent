-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : jeu. 18 avr. 2024 à 19:28
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `heavent`
--

-- --------------------------------------------------------

--
-- Structure de la table `eventsheavent`
--

CREATE TABLE `eventsheavent` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `date` date NOT NULL,
  `location` varchar(255) NOT NULL,
  `places` int(255) NOT NULL,
  `User` varchar(255) NOT NULL,
  `prix` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `eventsheavent`
--

INSERT INTO `eventsheavent` (`id`, `name`, `description`, `date`, `location`, `places`, `User`, `prix`) VALUES
(1, 'event1', 'description1', '2024-02-28', 'Mariage', 0, 'bobi', 1),
(2, 'event_name', 'event_description', '2024-02-17', 'Exposition', 100, 'michel@michel.fr', 50),
(3, 'flukyfight', 'Sans écharde !', '2024-04-12', 'Conference', 20, 'fluky@hotmail.fr', 15.5),
(4, 'Présentation', 'Présentation du projet', '2024-04-24', 'Conference', 3, 'fluky@hotmail.fr', 15.5);

-- --------------------------------------------------------

--
-- Structure de la table `inscriptionheavent`
--

CREATE TABLE `inscriptionheavent` (
  `inscription_id` int(11) NOT NULL,
  `user_Email` varchar(255) NOT NULL,
  `event_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `inscriptionheavent`
--

INSERT INTO `inscriptionheavent` (`inscription_id`, `user_Email`, `event_id`) VALUES
(3, 'user@example.com', 3),
(5, 'user@example.com', 1),
(8, 'fluky@hotmail.fr', 4);

-- --------------------------------------------------------

--
-- Structure de la table `notificationsheavent`
--

CREATE TABLE `notificationsheavent` (
  `id` int(11) NOT NULL,
  `event_id` int(11) DEFAULT NULL,
  `notification_type` varchar(255) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `notificationsheavent`
--

INSERT INTO `notificationsheavent` (`id`, `event_id`, `notification_type`, `date`) VALUES
(2, 1, 'Modification', '2024-04-15 12:44:49'),
(3, 2, 'Modification', '2024-04-15 16:09:13'),
(4, 1, 'Modification', '2024-04-15 16:40:15'),
(5, 3, 'Modification', '2024-04-15 23:14:54'),
(6, 3, 'Modification', '2024-04-18 16:43:59');

-- --------------------------------------------------------

--
-- Structure de la table `usersheavent`
--

CREATE TABLE `usersheavent` (
  `email` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `is_Admin` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `usersheavent`
--

INSERT INTO `usersheavent` (`email`, `username`, `password`, `is_Admin`) VALUES
('co@gmail.fr', 'Gerant', '110403', 0),
('fluky@hotmail.fr', 'fluky', '97532445', 0),
('saminochko@gmail.com', 'sam', 'sam', 1),
('user2@example.com', 'username', 'password', 0),
('user@example.com', 'Bob', 'new_password', 1);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `eventsheavent`
--
ALTER TABLE `eventsheavent`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `inscriptionheavent`
--
ALTER TABLE `inscriptionheavent`
  ADD PRIMARY KEY (`inscription_id`),
  ADD KEY `email` (`user_Email`),
  ADD KEY `event` (`event_id`);

--
-- Index pour la table `notificationsheavent`
--
ALTER TABLE `notificationsheavent`
  ADD PRIMARY KEY (`id`),
  ADD KEY `eventId` (`event_id`);

--
-- Index pour la table `usersheavent`
--
ALTER TABLE `usersheavent`
  ADD PRIMARY KEY (`email`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `eventsheavent`
--
ALTER TABLE `eventsheavent`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `inscriptionheavent`
--
ALTER TABLE `inscriptionheavent`
  MODIFY `inscription_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT pour la table `notificationsheavent`
--
ALTER TABLE `notificationsheavent`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `inscriptionheavent`
--
ALTER TABLE `inscriptionheavent`
  ADD CONSTRAINT `email` FOREIGN KEY (`user_Email`) REFERENCES `usersheavent` (`email`),
  ADD CONSTRAINT `event` FOREIGN KEY (`event_id`) REFERENCES `eventsheavent` (`id`);

--
-- Contraintes pour la table `notificationsheavent`
--
ALTER TABLE `notificationsheavent`
  ADD CONSTRAINT `notificationsheavent_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `eventsheavent` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

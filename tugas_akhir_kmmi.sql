-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 18 Okt 2021 pada 17.02
-- Versi server: 10.4.20-MariaDB
-- Versi PHP: 8.0.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tugas akhir kmmi`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `absensi`
--

CREATE TABLE `absensi` (
  `Id` int(11) NOT NULL,
  `Nama` text NOT NULL,
  `NIM` text NOT NULL,
  `Kelas` text NOT NULL,
  `Tanggal` text NOT NULL,
  `Kehadiran` enum('H','A','I','S') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `absensi`
--

INSERT INTO `absensi` (`Id`, `Nama`, `NIM`, `Kelas`, `Tanggal`, `Kehadiran`) VALUES
(2, 'Jaka taruf', '19223090', 'SI 4O', '5-9-2021\n', ''),
(4, 'Jaka taruf', '19223090', 'SI 4O0', '5-9-2021\n', ''),
(5, 'Jaka taruf', '19223090', 'SI 4O0', '5-9-2021\n', ''),
(6, 'Jaka taruf', '19223090', 'SI 4O0', '5-9-2021\n', ''),
(7, 'Jaka taruf', '19223090', 'SI 4O0', '5-9-2021\n', ''),
(8, 'Jaka taruf', '19223090', 'SI 4O0', '5-9-2021\n', ''),
(9, 'Jaka taruf', '19223090', 'SI 4O', '5-9-2021\n', ''),
(10, 'rrr', 'ftt', 'gtty', '18-9-2021\n', ''),
(11, 'rrr', 'ftt', 'gtty', '18-9-2021\n', ''),
(23, 'Hamdan', '19220090', 'si 5o', '19-9-2021\n', '');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `absensi`
--
ALTER TABLE `absensi`
  ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `absensi`
--
ALTER TABLE `absensi`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

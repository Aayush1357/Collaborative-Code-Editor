import Link from "next/link";

const Footer = () => {
  return (
    <footer className="text-gray-300 bg-black/30 backdrop-blur-lg mx-3 my-9 p-8 rounded-xl shadow-lg shadow-blue-400/20 border border-blue-400/30 flex flex-col md:flex-row justify-between items-center gap-4 text-center">
      <p>&copy; 2024 Collaborative Code Editor. All rights reserved.</p>
      <nav className="flex flex-wrap justify-center gap-4">
        <Link
          href="/"
          className="hover:text-blue-400 transition-all duration-300"
        >
          Privacy Policy
        </Link>
        <Link
          href="/"
          className="hover:text-blue-400 transition-all duration-300"
        >
          Terms of Service
        </Link>
        <Link
          href="/"
          className="hover:text-blue-400 transition-all duration-300"
        >
          Contact Us
        </Link>
      </nav>
    </footer>
  );
};

export default Footer;

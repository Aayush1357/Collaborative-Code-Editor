import Navbar from "@/components/Navbar";
import HeroSection from "@/components/Hero";
import Features from "@/components/Features";
import Footer from "@/components/Footer";

export default function Home() {
  return (
    <div>
      <div><Navbar /></div>
      <div><HeroSection /></div>
      <div><Features/></div>
      <div><Footer/></div>
    </div>
  );
}

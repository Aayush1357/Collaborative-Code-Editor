import { Code, Users, ShieldCheck } from "lucide-react";

const Features = () => {
  const features = [
    {
      icon: <Code size={48} className="text-blue-400" />,
      title: "Real-time Collaboration",
      description: "Collaborate with your team in real-time with seamless code syncing and shared sessions."
    },
    {
      icon: <Users size={48} className="text-blue-400" />,
      title: "Multi-Language Support",
      description: "Write code in multiple languages with syntax highlighting and AI-powered assistance."
    },
    {
      icon: <ShieldCheck size={48} className="text-blue-400" />,
      title: "Secure & Reliable",
      description: "Experience end-to-end encryption and auto-backup for all your coding projects."
    }
  ];

  return (
    <section className="py-16 mx-3">
      <h2 className="text-3xl md:text-4xl font-bold text-center mb-12 text-white">Why Choose Our Collaborative Code Editor?</h2>
      <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
        {features.map((feature, index) => (
          <div
            key={index}
            className="p-8 bg-black/30 backdrop-blur-lg rounded-2xl border border-blue-400/30 shadow-lg shadow-blue-400/20 hover:shadow-blue-400/50 transform hover:scale-105 transition-all duration-300"
          >
            <div className="mb-4">{feature.icon}</div>
            <h3 className="text-2xl font-semibold mb-2 text-blue-400">{feature.title}</h3>
            <p className="text-gray-300">{feature.description}</p>
          </div>
        ))}
      </div>
    </section>
  );
};

export default Features;